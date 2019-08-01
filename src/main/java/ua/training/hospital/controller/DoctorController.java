package ua.training.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.service.user.UserService;

@Controller
public class DoctorController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/doctor/page", method = RequestMethod.GET)
    public String defaultGetDoctorPage(Model model){
        return getDoctorPage(0,10,model);
    }

    @RequestMapping(value = "/doctor/page/{pageNumber}", method = RequestMethod.GET)
    public String getDoctorPage(@PathVariable(required = false) int pageNumber, @RequestParam(defaultValue = "10") int recordsPerPage, Model model) {
        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(recordsPerPage < 1){
            recordsPerPage = 1;
        }

        Page<ShowUserToDoctorDTO> page = userService.findPatientsToShow(pageNumber,recordsPerPage);
        model.addAttribute("page", page);

        return "doctor/doctorPage";
    }
}
