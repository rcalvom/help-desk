package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AgentController {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private UserDAO userDAO;

    //Mis solicitudes
    @GetMapping("agent/my-requests")
    public String myRequestsAgentDefault(Model model){
        User user = userDAO.selectAgent("agent1");
        List<Request> requests = (List<Request>) requestDAO.selectByAgent(user);
        model.addAttribute("Requests", requests);
        return "my-requests-agent";
    }

    //Detalles de la solicitud agente
    @GetMapping("agent/details/{id}")
    public String requestDetailsAgentDefault(@PathVariable("id") String id, Model model){
        Request RequestDetail = requestDAO.selectById(id);
        model.addAttribute("requestDetail", RequestDetail);
        return "request-details-agent";
    }

    @PostMapping("agent/details/{id}")
    public String requestDetailsAgentPost(@PathVariable("id") String id, Model model){
        return "request-details-agent";
    }
    
}
