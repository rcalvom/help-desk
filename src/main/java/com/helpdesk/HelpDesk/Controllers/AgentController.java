package com.helpdesk.HelpDesk.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AgentController {

    // Controladores del Agente

    //Mis solicitudes
    @GetMapping("agent/my-requests")
    public String myRequestsAgentDefault(Model model){
        //List<Request> requests = new ArrayList<Request>();
        // requests = lista con las solicitudes de la base de datos filtrada por el usuario
        // model.addAttribute("Requests", requests);
        return "my-requests-agent";
    }
    @PostMapping("agent/my-requests")
    public String myRequestsAgentPost(Model model){
        return "my-requests-agent";
    }

    //Detalles de la solicitud agente
    @GetMapping("agent/details/{id}")
    public String requestDetailsAgentDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // Request RequestDetail = Buscar la solicitud en la BD
        // model.addAttribute("requestDetail", RequestRetail;
        return "request-details-agent";
    }

    @PostMapping("agent/details/{id}")
    public String requestDetailsAgentPost(@PathVariable("id") String id, Model model){
        return "request-details-agent";
    }
    
}
