package ua.training.hospital.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        logger.debug("requested /registrayion");
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        logger.debug("returning register.jsp page to user");
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
        logger.debug("requested /registrayion post method");
        Optional<User> registered = Optional.empty();
        if (!result.hasErrors()) {
            try {
                registered = userService.registerUser(user);

                if (registered.isPresent()) {
                    ModelAndView success = new ModelAndView("login");
                    success.addObject("registered", true);
                    logger.debug("registration succes, returning login.jsp page");
                    return success;
                } else {
                    logger.info("userService.registerUser returned empty object rejecting emil with registartion error message");
                    result.rejectValue("email", "registration.error");
                }
            } catch (EmailExistsException ex) {
                logger.info("trying to register with existing email ");
                result.rejectValue("email", "email.exists");
            }
        }
        logger.debug("registration unsuccesfull, returning register.jsp page back");
        return new ModelAndView("register", "user", user);
    }
}
