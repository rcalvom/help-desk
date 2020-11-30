package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.FeedbackDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.CreateRequestForm;
import com.helpdesk.HelpDesk.Forms.FeedbackForm;
import com.helpdesk.HelpDesk.Models.Feedback;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private final UserDAO userDAO;
    private final RequestDAO requestDAO;
    private final FeedbackDAO feedbackDAO;

    public UserController(UserDAO userDAO, RequestDAO requestDAO, FeedbackDAO feedbackDAO) {
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
        this.feedbackDAO = feedbackDAO;
    }

    //Crear solicitud
    @GetMapping("/user/create-request")
    public String createRequestUserDefault(Model model) {
        if (userDAO.selectPerson(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]) != null){
            model.addAttribute("createRequestForm", new CreateRequestForm());
            this.header(model);
            return "create-request-user";
        }else{
            return "redirect:/error/403";
        }
    }

    @PostMapping("/user/create-request")
    public String createRequestUserPost(@ModelAttribute CreateRequestForm form, Model model){
        User user = userDAO.selectPerson(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user != null) {
            Request request = new Request(form.getDescription(), user, form.getInventoryPlate(), form.getEquipmentNumber());
            requestDAO.insert(request);
            this.header(model);
            return "redirect:/user/my-requests";
        }else{
            return "redirect:/error/403";
        }
    }

    //Mis solicitudes
    @GetMapping("/user/my-requests")
    public String myRequestsUserDefault(Model model){
        User user = userDAO.selectPerson(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user != null) {
            List<Request> requests = (List<Request>)  requestDAO.selectByUser(user);
            List<Request> requestsAc = new ArrayList<>();
            List<Request> requestsCl = new ArrayList<>();
            List<Request> requestsUn = new ArrayList<>();
            for(Request req : requests){
                if(req.getStatus() == Request.Status.ACTIVO || req.getStatus() == Request.Status.NO_ASIGNADO){
                    requestsAc.add(req);
                }else{
                    requestsCl.add(req);
                    if(req.getFeedback() == null) {
                        requestsUn.add(req);
                    }
                }
            }
            model.addAttribute("RequestsAc", requestsAc);
            model.addAttribute("RequestsCl", requestsCl);
            model.addAttribute("RequestsUn", requestsUn);
            this.header(model);
            return "my-requests-user";
        }else{
            return "redirect:/error/403";
        }
    }

    //Detalles de la solicitud usuario
    @GetMapping("/user/details/{id}")
    public String requestDetailsUserDefault(@PathVariable("id") String id, Model model){
        User user = userDAO.selectPerson(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        Request RequestDetail = requestDAO.selectById(id);
        if(user != null){
            if(user.getUsername().equals(RequestDetail.getUser().getUsername())){
                model.addAttribute("requestDetail", RequestDetail);
                this.header(model);
                return "request-details-user";
            }
        }
        return "redirect:/error/403";
    }

    //Calificar solicitud
    @GetMapping("/user/feedback/{id}")
    public String feedbackDefault(@PathVariable("id") String id, Model model){
        model.addAttribute("feedbackForm", new FeedbackForm());
        this.header(model);
        return "feedback-user";
    }

    @PostMapping("/user/feedback/{id}")
    public String feedbackPost(@ModelAttribute FeedbackForm form, @PathVariable("id") String id, Model model) {
        header(model);
        Feedback feedback = new Feedback(form.getSpecification(), form.getRating(), form.getSuccessful());
        feedbackDAO.insert(feedback);
        Request request = requestDAO.selectById(id);
        Request newRequest = new Request();
        newRequest.setId(request.getId());
        newRequest.setSpecification(request.getSpecification());
        newRequest.setCreationDate(request.getCreationDate());
        newRequest.setEndingDate(request.getEndingDate());
        newRequest.setStatus(Request.Status.CERRADO);
        newRequest.setInventoryPlate(request.getInventoryPlate());
        newRequest.setEquipmentNumber(request.getEquipmentNumber());
        newRequest.setUser(request.getUser());
        newRequest.setAgents(request.getAgents());
        newRequest.setCategory(request.getCategory());
        newRequest.setFeedback(feedback);
        requestDAO.update(request, newRequest);
        return "redirect:/user/my-requests";
    }

    //FAQ
    @GetMapping("/FAQ")
    public String FAQ(){
        return "FAQ";
    }

    private void header(Model model){
        model.addAttribute("name", Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("given_name")));
        if(userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])!=null){
            model.addAttribute("isAgent", true);
        }else{
            model.addAttribute("isAgent", false);
        }
    }

}
