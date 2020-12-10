package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.BoundingTypeDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.DataLoginForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    private final BoundingTypeDAO boundingTypeDAO;
    private final DependencyDAO dependencyDAO;
    private final UserDAO userDAO;

    public LoginController(BoundingTypeDAO boundingTypeDAO, DependencyDAO dependencyDAO, UserDAO userDAO) {
        this.boundingTypeDAO = boundingTypeDAO;
        this.dependencyDAO = dependencyDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("/login")
    public String loginDefault(){
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model) throws NullPointerException{
        User user = this.userDAO.selectPerson(((String)(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
        if(user == null){
            DataLoginForm form = new DataLoginForm();
            form.setName(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("name")));
            form.setUsername(((String)(Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0]);
            model.addAttribute("dataLogin", form);
            List<BoundingType> boundingTypeList = (List<BoundingType>) this.boundingTypeDAO.select();
            model.addAttribute("boundingTypes", boundingTypeList);
            List<Dependency> dependencies = (List<Dependency>) this.dependencyDAO.select();
            model.addAttribute("dependencies", dependencies);
            model.addAttribute("Admin", userDAO.selectAdmin()==null ? true: false);
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
        User user = new User(form.getUsername(), form.getName(), this.boundingTypeDAO.select(form.getBoundingType()), this.dependencyDAO.select(form.getDependency()), form.getLocation());
        user.setPhone(form.getPhone());
        user.setPhoneExtension(form.getPhoneExtension());
        if(form.getAdminCode() == 12345678 && this.userDAO.selectAdmin() == null){
            user.setAdministrator(true);
            user.setAgent(true);
            this.userDAO.insert(user);
            return "redirect:/admin/inbox";
        }
        this.userDAO.insert(user);
        return "redirect:/user/create-request";
    }

    // FAQ
    @GetMapping("/FAQ")
    public String FAQ(){
        return "FAQ";
    }

}
