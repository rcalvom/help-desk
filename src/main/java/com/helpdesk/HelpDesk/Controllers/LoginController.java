package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.BoundingTypeDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.Forms.DataLogginForm;
import com.helpdesk.HelpDesk.Forms.LoginForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private BoundingTypeDAO boundingTypeDAO;

    @Autowired
    private DependencyDAO dependencyDAO;

    @GetMapping({"/", "/login"})
    public String loginDefault(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping({"/", "/login"})
    public String loginPost(@ModelAttribute LoginForm form){
        switch (form.getUsername()) {
            case "user":
                return "redirect:/user/create-request";
            case "agent":
                return "redirect:/agent/my-requests";
            case "admin":
                return "redirect:/admin/inbox";
        } // TODO: Conectar con la base de datos para el login
        return "login";
    }

    @GetMapping("/data-login")
    public String dataLoggingDefault(Model model){
        DataLogginForm form = new DataLogginForm();
        model.addAttribute("dataLogging", form);
        List<BoundingType> boundingTypeList = (List<BoundingType>) boundingTypeDAO.select();
        model.addAttribute("boundingTypes", boundingTypeList);
        List<Dependency> dependencies = (List<Dependency>) dependencyDAO.select();
        model.addAttribute("dependencies", dependencies);
        return "data-logging";
    }

    @PostMapping("/data-login")
    public String dataLoggingPost(@ModelAttribute DataLogginForm form){
        // TODO: Registrar los datos del usuario
        return "redirect:/data-login";
    }
}
