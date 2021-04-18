package ua.training.hospital.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class startPageController {
    @RequestMapping("/")
    public String startPage(Authentication authentication){
        return "redirect:" + HospitalAuthenticationSuccessHandler.determineTargetUrl(authentication);
    }
}
