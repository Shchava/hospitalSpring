package ua.training.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.training.hospital.entity.exceptions.EmailExistsException;
import ua.training.hospital.service.user.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

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
        Optional<User> registered = Optional.empty();
        if (!result.hasErrors()) {
            try {
                registered = userService.registerUser(user);

                if (registered.isPresent()) {
                    ModelAndView success = new ModelAndView("login");
                    success.addObject("registered", true);
                    return success;
                } else {
                    result.rejectValue("email", "registration.error");
                }
            } catch (EmailExistsException ex) {
                result.rejectValue("email", "email.exists");
            }
        }
        return new ModelAndView("register", "user", user);
    }
}
