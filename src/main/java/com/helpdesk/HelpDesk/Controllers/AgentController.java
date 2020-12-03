package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.AgentReportForm;
import com.helpdesk.HelpDesk.Forms.ReportRatingForm;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
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

    private final RequestDAO requestDAO;
    private final UserDAO userDAO;

    public AgentController(RequestDAO requestDAO, UserDAO userDAO) {
        this.requestDAO = requestDAO;
        this.userDAO = userDAO;
    }

    //Mis solicitudes
    @GetMapping("/agent/my-requests")
    public String myRequestsAgentDefault(Model model){
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
            this.header(model);
            return "my-requests-agent";
        }else {
            return "redirect:/error/403";
        }
    }

    //Detalles de la solicitud agente
    @GetMapping("/agent/details/{id}")
    public String requestDetailsAgentDefault(@PathVariable("id") String id, Model model){
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        Request RequestDetail = requestDAO.selectById(id);
        if(user != null){
            for(User u : RequestDetail.getAgents()){
                if(u.getUsername().equals(user.getUsername())){
                    model.addAttribute("requestDetail", RequestDetail);
                    this.header(model);
                    return "request-details-agent";
                }
            }
        }
        return "redirect:/error/403";
    }

    @PostMapping("/agent/details/{id}")
    public String requestDetailsAgentPost(@PathVariable("id") String id, Model model){
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        Request request = requestDAO.selectById(id);
        //System.out.println("El estatus de la request es: " + request.getStatus());
        if(user != null){
            for(User u : request.getAgents()){
                if(u.getUsername().equals(user.getUsername())) {
                    /*Request newRequest = requestDAO.selectById(id);
                    newRequest.setStatus(Request.Status.CERRADO_SIN_CALIFICACION);
                    newRequest.setEndingDate(Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00")));*/

                    Request newRequest = new Request(request.getSpecification(), request.getUser(), request.getInventoryPlate(), request.getEquipmentNumber());
                    newRequest.setId(request.getId());
                    newRequest.setCategory(request.getCategory());
                    newRequest.setAgents(request.getAgents());
                    newRequest.setStatus(Request.Status.CERRADO_SIN_CALIFICACION);
                    newRequest.setEndingDate(Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00")));
                    newRequest.setCreationDate(request.getCreationDate());
                    /*System.out.println("el id es: " + newRequest.getId());
                    System.out.println("El estatus de la request es: " + request.getStatus() + newRequest.getStatus());*/
                    requestDAO.update(request, newRequest);
                    this.header(model);
                    return "redirect:/agent/my-requests";
                }
            }
        }
        return "redirect:/error/403";
    }

    @GetMapping("/agent/my-metrics")
    public String metricsAgentDefault(Model model){
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user != null) {
            boolean[] toShow = {true,true,true,true,true};
            AgentReportForm agentReportForm = new AgentReportForm(user,toShow);
            model.addAttribute("agentReportForm", agentReportForm);
            this.header(model);
            return "my-metrics-agent";
        }
        return "redirect:/error/403";
    }

    private void header(Model model){
        model.addAttribute("name",(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("given_name"))));
    }
    
}
