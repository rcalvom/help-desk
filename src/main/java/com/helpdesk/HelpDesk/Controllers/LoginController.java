package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.BoundingTypeDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.DataLoginForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private BoundingTypeDAO boundingTypeDAO;

    @Autowired
    private DependencyDAO dependencyDAO;

    @Autowired
    private UserDAO userDAO;

    @GetMapping({"/", "/login"})
    public String loginDefault(Model model){
        // model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model) throws NullPointerException{
        User user = userDAO.selectPerson(((String)(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user == null){
            DataLoginForm form = new DataLoginForm();
            form.setName(((String)(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("name")))));
            form.setUsername(((String)(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
            model.addAttribute("dataLogin", form);
            List<BoundingType> boundingTypeList = (List<BoundingType>) boundingTypeDAO.select();
            model.addAttribute("boundingTypes", boundingTypeList);
            List<Dependency> dependencies = (List<Dependency>) dependencyDAO.select();
            model.addAttribute("dependencies", dependencies);
            return "data-login";
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

    @PostMapping("/loginSuccess")
    public String dataLoggingPost(@ModelAttribute DataLoginForm form){
        User user = new User(form.getUsername(), form.getName(), boundingTypeDAO.select(form.getBoundingType()), dependencyDAO.select(form.getDependency()));
        userDAO.insert(user);
        return "redirect:/user/create-request";
    }

}
