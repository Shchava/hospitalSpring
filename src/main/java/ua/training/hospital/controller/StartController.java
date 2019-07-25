package ua.training.hospital.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartController {
    @RequestMapping(value = "/tr", method= RequestMethod.GET)
    public String index(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return "index";
    }
}
