package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.BoundingTypeDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.DataLogginForm;
import com.helpdesk.HelpDesk.Forms.LoginForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Models.User;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private UserDAO userDAO;

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

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model, OAuth2AuthenticationToken authentication, RedirectAttributes redirectAttributes) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();

            assert userAttributes != null;
            String username = ((String) userAttributes.get("email")).split("@")[0];
            User user = userDAO.selectUser(username);
            if(user == null){
                redirectAttributes.addFlashAttribute("username", username);
                redirectAttributes.addFlashAttribute("name", userAttributes.get("name"));
                return "redirect:/data-login";
            }else{
                if(user.isAdministrator()){
                    return "redirect:/admin/inbox";
                }else if(user.isAgent()){
                    return "redirect:/agent/my-requests";
                }else{
                    return "redirect:/user/create-request";
                }
            }
        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }


}
