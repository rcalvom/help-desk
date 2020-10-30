package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.CategoryDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.AssignRequestForm;
import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    //Bandeja de entrada
    @GetMapping("/admin/inbox")
    public String inboxRequestsAdminDefault(Model model){
        List<Request> requests = (List<Request>) requestDAO.selectByStatus("NO_ASIGNADO");
        model.addAttribute("Requests", requests);
        return "inbox-requests-admin";
    }

    //Asignar solicitud agente
    @GetMapping("/admin/assign-request/{id}")
    public String assignRequestAdminDefault(@PathVariable("id") String id, Model model){

        Request request = requestDAO.selectById(id).iterator().next();

        AssignRequestForm req = new AssignRequestForm(request.getId(),request.getCreationDate().getTime().toString(),request.getUser().getUsername(),request.getSpecification());
        model.addAttribute("assignRequest", req);
        List<User> agt = (List<User>) userDAO.selectAgent();
        model.addAttribute("agents", agt);
        List<Category> ctg = (List<Category>) categoryDAO.select();
        model.addAttribute("category", ctg);
        return "assign-request-admin";
    }

    @PostMapping("/admin/assign-request/{id}")
    public String assignRequestAdminPost(@PathVariable("id") String id, @ModelAttribute AssignRequestForm form, Model model){

        Request request = requestDAO.selectById(id).iterator().next();
        Request newRequest = new Request();
        newRequest.setId(request.getId());
        newRequest.setSpecification(request.getSpecification());
        newRequest.setCreationDate(request.getCreationDate());
        newRequest.setStatus(Request.Status.ACTIVO);
        newRequest.setInventoryPlate(form.getInventoryPlate());
        newRequest.setEquipmentNumber(form.getEquipmentNumber());
        newRequest.setUser(request.getUser());
        newRequest.setAgents(new HashSet<>());
        newRequest.getAgents().add(userDAO.selectAgent(form.getAgentUsername()).iterator().next());
        newRequest.setCategory(categoryDAO.select(form.getCategory()).iterator().next());
        requestDAO.update(request, newRequest);

        return "redirect:/admin/requests";
    }

    // Solicitudes del sistema
    @GetMapping("/admin/requests")
    public String requestsAdminDefault(Model model){
        List<Request> requests = (List<Request>) requestDAO.select();
        model.addAttribute("Requests", requests);
        return "requests-admin";
    }

    //Detalles de la solicitud administrador
    @GetMapping("/admin/details/{id}")
    public String requestDetailsAdminDefault(@PathVariable("id") String id, Model model){
        Request RequestDetail = requestDAO.selectById(id).iterator().next();
        model.addAttribute("requestDetail", RequestDetail);
        return "request-details-admin";
    }

}

