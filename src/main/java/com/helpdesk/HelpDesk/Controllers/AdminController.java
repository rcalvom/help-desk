package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.CategoryDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.AssignRequestForm;
import com.helpdesk.HelpDesk.Forms.CategoryForm;
import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Request> requests = (List<Request>) requestDAO.selectByStatus(Request.Status.NO_ASIGNADO);
        model.addAttribute("Requests", requests);
        return "inbox-requests-admin";
    }

    //Asignar solicitud agente
    @GetMapping("/admin/assign-request/{id}")
    public String assignRequestAdminDefault(@PathVariable("id") String id, Model model){
        // TODO: Comprobar que la solicitud no haya sido asignada.
        Request request = requestDAO.selectById(id);
        AssignRequestForm req = new AssignRequestForm(request.getId(), request.formatCreationDate(), request.getUser().getUsername(), request.getSpecification());
        model.addAttribute("assignRequest", req);
        List<User> agt = (List<User>) userDAO.selectAgent();
        model.addAttribute("agents", agt);
        List<Category> ctg = (List<Category>) categoryDAO.select();
        model.addAttribute("category", ctg);
        return "assign-request-admin";
    }

    @PostMapping("/admin/assign-request/{id}")
    public String assignRequestAdminPost(@PathVariable("id") String id, @ModelAttribute AssignRequestForm form, RedirectAttributes redirectAttributes){
        Request request = requestDAO.selectById(id);
        Request newRequest = new Request();
        newRequest.setId(request.getId());
        newRequest.setSpecification(request.getSpecification());
        newRequest.setCreationDate(request.getCreationDate());
        newRequest.setStatus(Request.Status.ACTIVO);
        newRequest.setInventoryPlate(form.getInventoryPlate());
        newRequest.setEquipmentNumber(form.getEquipmentNumber());
        newRequest.setUser(request.getUser());
        newRequest.setAgents(new HashSet<>());
        for(String username : form.getAgentUsername()){
            newRequest.getAgents().add(userDAO.selectAgent(username));
        }
        newRequest.setCategory(categoryDAO.select(form.getCategory()));
        if (!requestDAO.update(request, newRequest)){
            if(newRequest.getEquipmentNumber() > 10) {
                redirectAttributes
                        .addFlashAttribute("alert", "El número de equipos debe ser menor o igual a 10, solicitud \"" + id + " \"no asignada.")
                        .addFlashAttribute("clase", "danger");
            }else if(newRequest.getEquipmentNumber() <= 0) {
                redirectAttributes
                        .addFlashAttribute("alert", "El número de equipos debe ser un valor positivo, solicitud \"" + id + " \"no asignada.")
                        .addFlashAttribute("clase", "danger");
            }
        }
        return "redirect:/admin/inbox";
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
        Request RequestDetail = requestDAO.selectById(id);
        model.addAttribute("requestDetail", RequestDetail);
        return "request-details-admin";
    }

    //Gestionar categorias
    @GetMapping("/admin/categories")
    public String categotyManagmentAdminDefault(@RequestParam(value = "category", required = false) String category, Model model){
        List<Category> categories = (List<Category>) categoryDAO.select();
        model.addAttribute("categories", categories);
        model.addAttribute("newCategory", new CategoryForm());
        return "category-managment-admin";
    }

    @PostMapping("/admin/categories")
    public String categotyManagmentAdminPost(@RequestParam(value = "category", required = false) String category, @ModelAttribute CategoryForm form, Model model){
        System.out.println(category);
        System.out.println(form.getName());
        if(category!=null){
            Category cat= categoryDAO.select(category);
            categoryDAO.delete(cat);
        }else{
            Category cat= new Category();
            cat.setName(form.getName());
            categoryDAO.insert(cat);
        }
        return "redirect:/admin/categories";
    }

}

