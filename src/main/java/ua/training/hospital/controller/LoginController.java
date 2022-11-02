package ua.training.hospital.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false)String error,
                            @RequestParam(value = "logout", required = false)String logout,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("error",error != null);
        model.addAttribute("logout",logout != null);
        logger.debug("returning login.jsp to user");
        return "login";
    }
}
