package ua.training.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.User;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView register(
            @ModelAttribute("user") @Valid UserDTO user,
            BindingResult result,
            WebRequest request,
            Errors errors
    ) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = new User();//TODO register user
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("register", "user", user);
        }
        return new ModelAndView("register", "user", user);
    }
}
