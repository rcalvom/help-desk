package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.FeedbackDAO;
import com.helpdesk.HelpDesk.DAO.RatingDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.CreateRequestForm;
import com.helpdesk.HelpDesk.Forms.FeedbackForm;
import com.helpdesk.HelpDesk.Models.Feedback;
import com.helpdesk.HelpDesk.Models.Rating;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private RatingDAO ratingDAO;

    @Autowired
    private FeedbackDAO feedbackDAO;

    //Crear solicitud
    @GetMapping("/user/create-request")
    public String createRequestUserDefault(Model model){
        model.addAttribute("createRequestForm", new CreateRequestForm());
        return "create-request-user";
    }

    @PostMapping("/user/create-request")
    public String createRequestUserPost(@ModelAttribute CreateRequestForm form){
        User user = userDAO.selectUser("user");
        Request request = new Request(form.getDescription(), user, form.getInventoryPlate(), form.getEquipmentNumber());
        requestDAO.insert(request);
        return "redirect:/user/my-requests";
    }

    //Mis solicitudes
    @GetMapping("/user/my-requests")
    public String myRequestsUserDefault(Model model){
        User user = userDAO.selectUser("user");
        List<Request> requests = (List<Request>)  requestDAO.selectByUser(user);
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
        return "my-requests-user";
    }

    //Detalles de la solicitud usuario
    @GetMapping("/user/details/{id}")
    public String requestDetailsUserDefault(@PathVariable("id") String id, Model model){
        Request RequestDetail = requestDAO.selectById(id);
        model.addAttribute("requestDetail", RequestDetail);
        return "request-details-user";
    }

    //Calificar solicitud
    @GetMapping("/user/feedback/{id}")
    public String feedbackDefault(@PathVariable("id") String id, Model model){
        model.addAttribute("feedbackForm", new FeedbackForm());
        List<Rating> ratingsList = (List<Rating>) ratingDAO.select();
        model.addAttribute("rtg", ratingsList);
        return "feedback-user";
    }

    @PostMapping("/user/feedback/{id}")
    public String feedbackPost(@ModelAttribute FeedbackForm form, @PathVariable("id") String id, Model model) {
        Rating rating = new Rating();
        rating.setName(form.getRating());
        Feedback feedback = new Feedback(form.getSpecification(), rating);
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
    @GetMapping("/user/FAQ")
    public String FAQ(){
        return "FAQ";
    }

}
