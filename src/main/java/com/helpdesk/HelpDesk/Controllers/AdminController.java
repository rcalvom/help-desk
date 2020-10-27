package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.Forms.AssignRequestForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    // Controladores del Administrador
    //Bandeja de entrada
    @GetMapping("/admin/inbox")
    public String inboxRequestsAdminDefault(Model model){
        //List<Request> requests = new ArrayList<Request>();
        // requests =
        //requests = lista con las solicitudes de la base de datos filtrada por estado sin asignar
        // TODO: Crear esta busqueda en la BD
        //model.addAttribute("Requests", list);
        return "inbox-requests-admin";
    }
    @PostMapping("/admin/inbox")
    public String inboxRequestsAdminPost(Model model){
        return "inbox-requests-admin";
    }

    //Asignar solicitud agente
    @GetMapping("/admin/assign-request/{id}")
    public String assignRequestAdminDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // AssignRequestForm assignRequest = new AssignRequestForm("ID","CreationDate","userUsername","D1");
        // model.addAttribute("assignRequest", assignRequest);
        AssignRequestForm req = new AssignRequestForm("D1","D1","D1","D1");
        model.addAttribute("assignRequest", req);
        List<String> agt = new ArrayList<>();
        model.addAttribute("agents", agt); // Solo guardar los correos de los agentes
        List<String> ctg = new ArrayList<>();
        model.addAttribute("category", ctg); // solo guardar los nombres de las categorias
        return "assign-request-admin";
    }

    @PostMapping("/admin/assign-request/{id}")
    public String assignRequestAdminPost(@PathVariable("id") String id, @ModelAttribute AssignRequestForm form, Model model){
        System.out.println(form.getAgentUsername());
        return "redirect:/admin/requests";
    }

    // Solicitudes del sistema
    @GetMapping("/admin/requests")
    public String requestsAdminDefault(Model model){
        /* List<Request> requests = new ArrayList<Request>();
        requests = lista con todas las solicitudes de la base de datos
        // TODO: Crear esta busqueda en la BD
        model.addAttribute("Requests", list);*/
        return "requests-admin";
    }
    @PostMapping("/admin/requests")
    public String requestsAdminPost(Model model){
        return "requests-admin";
    }

    //Detalles de la solicitud administrador
    @GetMapping("admin/datails/{id}")
    public String requestDetailsAdminDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // Request RequestDetail = Buscar la solicitud en la BD
        // model.addAttribute("requestDetail", RequestRetail;
        return "request-details-admin";
    }

    @PostMapping("admin/details/{id}")
    public String requestDetailsAdminPost(@PathVariable("id") String id, Model model){
        return "request-details-admin";
    }

}
