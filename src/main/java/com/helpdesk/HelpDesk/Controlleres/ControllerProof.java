package com.helpdesk.HelpDesk.Controlleres;

import com.helpdesk.HelpDesk.Repository.BoundingTypeRepository;
import com.helpdesk.HelpDesk.Repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerProof {

    @Autowired
    BoundingTypeRepository boundingTypeRepository;

    @RequestMapping("/")
    public String controller(){
        System.out.println(boundingTypeRepository.getName("Prodfesor"));
        return "index";
    }



}
