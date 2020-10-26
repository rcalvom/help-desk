package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.Forms.CreateRequestForm;
import com.helpdesk.HelpDesk.Forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class MainControllers {

        // Vistas del Login

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
        if(form.getUserName().equals("user@unal.edu.co")){
            return "redirect:/create-request-user";
        }else if(form.getUserName().equals("agent@unal.edu.co")){
             return "redirect:/my-requests-agent";
        }else if(form.getUserName().equals("admin@unal.edu.co")){
            return "redirect:/inbox-requests-admin";
        }// TODO: Conectar con la base de datos para el login
        return "login";
    }

        // Controladores del Usuario
    //Crear solicitud
    @GetMapping("/create-request-user")
    public String createRequestUserDefault(Model model){
        model.addAttribute("createRequestForm", new CreateRequestForm());
        return "create-request-user";
    }
    @PostMapping("/create-request-user")
    public String createRequestUserPost(@ModelAttribute CreateRequestForm form, Model model){
        // TODO: Crear el objeto request con respecto al constructor
        // Request request = new Request(form.getDescription());
        return "redirect:/my-requests-user";
    }

    //Mis solicitudes
    @GetMapping("/my-requests-user")
    public String myRequestsUserDefault(Model model){
        // TODO: Hacer la busqueda de las solicitudes que ha pedido el usuario
        /* List<Request> requests = new ArrayList<Request>();
        requests = lista con las solicitudes de la base de datos filtrada por el usuario
        model.addAttribute("Requests", list);*/
        return "my-requests-user";
    }
    @PostMapping("/my-requests-user")
    public String myRequestsUserPost(Model model){
        // TODO: Revisar si este controlador sirve para algo
        return "my-requests-user";
    }

    //Detalles de la solicitud usuario
    @GetMapping("request-details-user/{id}")
    public String requestDetailsUserDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // Request RequestDetail = Buscar la solicitud en la BD
        // model.addAttribute("requestDetail", RequestRetail;
        return "request-details-user";
    }

    @PostMapping("request-details-user/{id}")
    public String requestDetailsUserPost(@PathVariable("id") String id, Model model){
        return "request-details-user";
    }

        // Controladores del Agente
    //Mis solicitudes
    @GetMapping("/my-requests-agent")
    public String myRequestsAgentDefault(Model model){
        /* List<Request> requests = new ArrayList<Request>();
        requests = lista con las solicitudes de la base de datos filtrada por el usuario

        model.addAttribute("Requests", list);*/
        return "my-requests-agent";
    }
    @PostMapping("/my-requests-agent")
    public String myRequestsAgentPost(Model model){
        return "my-requests-agent";
    }

    //Detalles de la solicitud agente
    @GetMapping("request-details-agent/{id}")
    public String requestDetailsAgentDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // Request RequestDetail = Buscar la solicitud en la BD
        // model.addAttribute("requestDetail", RequestRetail;
        return "request-details-agent";
    }

    @PostMapping("request-details-agent/{id}")
    public String requestDetailsAgentPost(@PathVariable("id") String id, Model model){
        return "request-details-agent";
    }


    // Controladores del Administrador
    //Bandeja de entrada
    @GetMapping("/inbox-requests-admin")
    public String inboxRequestsAdminDefault(Model model){
        /* List<Request> requests = new ArrayList<Request>();
        requests = lista con las solicitudes de la base de datos filtrada por estado sin asignar
        // TODO: Crear esta busqueda en la BD
        model.addAttribute("Requests", list);*/
        return "inbox-requests-admin";
    }
    @PostMapping("/inbox-requests-admin")
    public String inboxRequestsAdminPost(Model model){
        return "inbox-requests-admin";
    }

    //Detalles de la solicitud agente
    @GetMapping("assign-request-admin/{id}")
    public String assignRequestAdminDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // Request RequestDetail = Buscar la solicitud en la BD
        // model.addAttribute("requestDetail", RequestRetail;
        return "request-details-agent";
    }

    @PostMapping("assign-request-admin/{id}")
    public String assignRequestAdminPost(@PathVariable("id") String id, Model model){
        return "assign-request-admin";
    }

    // Solicitudes del sistema
    @GetMapping("/requests-admin")
    public String requestsAdminDefault(Model model){
        /* List<Request> requests = new ArrayList<Request>();
        requests = lista con todas las solicitudes de la base de datos
        // TODO: Crear esta busqueda en la BD
        model.addAttribute("Requests", list);*/
        return "requests-admin";
    }
    @PostMapping("/requests-admin")
    public String requestsAdminPost(Model model){
        return "requests-admin";
    }

    //Detalles de la solicitud administrador
    @GetMapping("request-details-admin/{id}")
    public String requestDetailsAdminDefault(@PathVariable("id") String id, Model model){
        // TODO: Con el id del formilario pasar toda la información a un objeto request
        // Request RequestDetail = Buscar la solicitud en la BD
        // model.addAttribute("requestDetail", RequestRetail;
        return "request-details-admin";
    }

    @PostMapping("request-details-admin/{id}")
    public String requestDetailsAdminPost(@PathVariable("id") String id, Model model){
        return "request-details-admin";
    }


}

// TODO: De grado general, revisar porque con la anotación @PathVariable no funcionan los menu desplegables


