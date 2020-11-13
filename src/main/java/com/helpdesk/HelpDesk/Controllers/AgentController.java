package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
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

import java.util.List;
import java.util.Objects;

@Controller
public class AgentController {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private UserDAO userDAO;

    //Mis solicitudes
    @GetMapping("/agent/my-requests")
    public String myRequestsAgentDefault(Model model){
        User user = userDAO.selectAgent(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user != null){
            List<Request> requests = (List<Request>) requestDAO.selectByAgent(user);
            model.addAttribute("Requests", requests);
            return "my-requests-agent";
        }else {
            return "redirect:/error";
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
                    return "request-details-agent";
                }
            }
        }
        return "redirect:/error";
    }

    @PostMapping("/agent/details/{id}")
    public String requestDetailsAgentPost(@PathVariable("id") String id, Model model){
        return "request-details-agent";
    }
    
}
