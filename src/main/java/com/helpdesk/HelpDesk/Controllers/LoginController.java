package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.Forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

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
}
