package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.BoundingTypeDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.Forms.DataLogginForm;
import com.helpdesk.HelpDesk.Forms.LoginForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

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

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    private final Map<String, String> oauth2AuthenticationUrls = new HashMap<>();


    @GetMapping({"/", "/login"})
    public String loginDefault(Model model){
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

    @PostMapping("/logout")
    public String logoutPost(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model, OAuth2AuthenticationToken authentication){
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            assert userAttributes != null;
            model.addAttribute("name", userAttributes.get("name"));
            model.addAttribute("email", userAttributes.get("email"));
            System.out.println(userAttributes.get("name"));
            System.out.println("ola");
        }else{
            System.out.println("bai");
        }

        return "login";
    }


}
