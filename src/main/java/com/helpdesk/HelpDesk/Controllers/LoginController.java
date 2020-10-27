package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.Forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")       // Vista auxiliar para la redirección del login
    public String login(){
        return "redirect:/";
    }

    @GetMapping("/")
    public String loginDefault(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/")
    public String loginPost(@ModelAttribute LoginForm form, Model model){
        // System.out.println(form.getUserName()); tests
        // System.out.println(form.getPassword());
        if(form.getUsername().equals("user@unal.edu.co")){
            return "redirect:/create-request-user";
        }else if(form.getUsername().equals("agent@unal.edu.co")){
            return "redirect:/my-requests-agent";
        }else if(form.getUsername().equals("admin@unal.edu.co")){
            return "redirect:/inbox-requests-admin";
        }// TODO: Conectar con la base de datos para el login
        return "login";
    }

}
