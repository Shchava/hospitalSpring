package ua.training.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.hospital.controller.utils.PaginationUtils;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.user.UserService;

import java.util.Optional;

@Controller
public class ShowPatientController {
    @Autowired
    UserService userService;

    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    PaginationUtils paginationUtils;

    @RequestMapping(value = "/doctor/patient{idPatient}", method = RequestMethod.GET)
    public String defaultShowPatient(@PathVariable long idPatient,@RequestParam(defaultValue = "10") int recordsPerPage,Model model){
        return getShowPatientPage(idPatient,0,recordsPerPage,model);
    }

    @RequestMapping(value = "/doctor/patient{idPatient}/{pageNumber}", method = RequestMethod.GET)
    public String getShowPatientPage(@PathVariable long idPatient,
                                @PathVariable int pageNumber,
                                @RequestParam(defaultValue = "10") int recordsPerPage, Model model) {

        pageNumber = paginationUtils.checkPageNumber(pageNumber);
        recordsPerPage = paginationUtils.checkRecordsPerPage(recordsPerPage);

        Optional<User> patient = userService.getUser(idPatient);
        Page<Diagnosis> diagnoses = diagnosisService.findDiagnosesByPatientId(pageNumber,recordsPerPage,idPatient);

        model.addAttribute("patient",patient.get());
        model.addAttribute("page",diagnoses); //TODO fix another user database query

        return "doctor/showPatient";
    }
}
