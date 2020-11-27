package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.ReportRatingForm;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class AgentController {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private UserDAO userDAO;

    //Mis solicitudes
    @GetMapping("/agent/my-requests")
    public String myRequestsAgentDefault(Model model){
        header(model);
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user != null){
            List<Request> requests = (List<Request>) requestDAO.selectByAgent(user);
            List<Request> requestsAc = new ArrayList<>();
            List<Request> requestsCl = new ArrayList<>();
            for(Request req : requests){
                if(req.getStatus() == Request.Status.ACTIVO || req.getStatus() == Request.Status.NO_ASIGNADO){
                    requestsAc.add(req);
                }
                else{
                    requestsCl.add(req);
                }
            }
            model.addAttribute("RequestsAc", requestsAc);
            model.addAttribute("RequestsCl", requestsCl);
            return "my-requests-agent";
        }else {
            return "redirect:/error";
        }
    }

    //Detalles de la solicitud agente
    @GetMapping("/agent/details/{id}")
    public String requestDetailsAgentDefault(@PathVariable("id") String id, Model model){
        header(model);
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        Request RequestDetail = requestDAO.selectById(id);
        if(user != null){
            for(User u : RequestDetail.getAgents()){
                if(u.getUsername().equals(user.getUsername())){
                    model.addAttribute("requestDetail", RequestDetail);
                    return "request-details-agent";
                }
            }
        }
        return "redirect:/error";
    }

    @PostMapping("/agent/details/{id}")
    public String requestDetailsAgentPost(@PathVariable("id") String id, Model model){
        header(model);
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        Request request = requestDAO.selectById(id);
        if(user != null){
            for(User u : request.getAgents()){
                if(u.getUsername().equals(user.getUsername())) {
                    Request newRequest = requestDAO.selectById(id);
                    newRequest.setStatus(Request.Status.CERRADO_SIN_CALIFICACION);
                    newRequest.setEndingDate(Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00")));
                    requestDAO.update(request, newRequest);
                    return "redirect:/agent/my-requests";
                }
            }
        }
        return "redirect:/error";
    }

    @GetMapping("/agent/my-metrics")
    public String metricsAgentDefault(Model model){
        header(model);
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user != null) {
            ReportRatingForm reportRatingForm = new ReportRatingForm();
            List<Request> requests = (List<Request>) requestDAO.selectByAgent(user);
            reportRatingForm.setName(user.getName());
            reportRatingForm.setTotal(requests.size());
            int E = 0,G = 0,R = 0,B = 0,D = 0;
            for(Request req : requests){
                if(req.getFeedback()!=null){
                    int rating = req.getFeedback().getRating();
                    switch (rating){
                        case 5:
                            E += 1;
                            break;
                        case 4:
                            G += 1;
                            break;
                        case 3:
                            R += 1;
                            break;
                        case 2:
                            B += 1;
                            break;
                        case 1:
                            D += 1;
                            break;
                    }
                }
            }
            reportRatingForm.setExcellent(E);
            reportRatingForm.setGood(G);
            reportRatingForm.setRegular(R);
            reportRatingForm.setBad(B);
            reportRatingForm.setDeficient(D);
            model.addAttribute("metrics", reportRatingForm);
            return "my-metrics-agent";
        }
        return "redirect:/error";
    }

    private void header(Model model){
        model.addAttribute("name",(String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("given_name"))));
    }
    
}
