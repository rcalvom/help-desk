package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.Forms.CreateRequestForm;
import com.helpdesk.HelpDesk.Forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if(form.getUserName().equals("savargas@unal.edu.co")){
            return "redirect:/create-request-user";
        }else if(form.getUserName().equals("rcalvom@unal.edu.co")){
             return "redirect:/my-requests-agent";
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

    //
    @RequestMapping("/request-details")
    public String a(){
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
}


/*
@RequestMapping(method = RequestMethod.DELETE, path = "/greeting/{id}")
   public void delete(@PathVariable Integer id)
         throws NoSuchRequestHandlingMethodException {
      try {
         data.removeGreeting(id);
      } catch (IndexOutOfBoundsException e) {
         throw new NoSuchRequestHandlingMethodException("greeting",
               GreetingController.class);
      }
   }
 */