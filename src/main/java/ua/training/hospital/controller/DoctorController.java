package ua.training.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.controller.dto.UserDTO;

@Controller
public class DoctorController {

    @RequestMapping(value = "/doctor/page", method = RequestMethod.GET)
    public String registration(Model model) {
        return "doctor/doctorPage";
    }
}
