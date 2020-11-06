package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.BoundingTypeDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.Forms.DataLogginForm;
import com.helpdesk.HelpDesk.Forms.LoginForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private BoundingTypeDAO boundingTypeDAO;

    @Autowired
    private DependencyDAO dependencyDAO;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    private final Map<String, String> oauth2AuthenticationUrls = new HashMap<>();


    @GetMapping({"/", "/login"})
    public String loginDefault(Model model){
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    /*@PostMapping({"/", "/login"})
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
    }*/

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
