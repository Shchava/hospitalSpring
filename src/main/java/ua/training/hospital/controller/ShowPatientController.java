package ua.training.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.controller.utils.PaginationUtils;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.User;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.user.UserService;

import javax.validation.Valid;
import java.security.Principal;
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
    public String defaultShowPatient(@PathVariable long idPatient, @RequestParam(defaultValue = "10") int recordsPerPage, Model model) {
        return getShowPatientPage(idPatient, 0, recordsPerPage, model);
    }

    @RequestMapping(value = "/doctor/patient{idPatient}/{pageNumber}", method = RequestMethod.GET)
    public String getShowPatientPage(@PathVariable long idPatient,
                                     @PathVariable int pageNumber,
                                     @RequestParam(defaultValue = "10") int recordsPerPage,
                                     Model model) {

        getDiagnoses(model, pageNumber, recordsPerPage, idPatient);
        model.addAttribute("newDiagnosis", new DiagnosisDTO());
        return "doctor/showPatient";
    }


    @RequestMapping(value = "/doctor/patient{idPatient}/addDiagnosis", method = RequestMethod.POST)
    public ModelAndView addDiagnosis(@PathVariable long idPatient,
                                     @ModelAttribute("newDiagnosis") @Valid DiagnosisDTO diagnosisDTO,
                                     @RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "10") int recordsPerPage,
                                     BindingResult result,
                                     Principal principal,
                                     Model model) {

        if (diagnosisService.addDiagnosis(diagnosisDTO, idPatient, principal.getName())) {
            model.addAttribute("addedDiagnosis", true);
            diagnosisDTO = new DiagnosisDTO();
        } else {
            result.rejectValue("name", "diagnosis.creationError");
        }

        getDiagnoses(model, pageNumber, recordsPerPage, idPatient);
        return new ModelAndView("doctor/showPatient", "newDiagnosis", diagnosisDTO);
    }

    private void getDiagnoses(Model model, int pageNumber, int recordsPerPage, long idPatient) {
        pageNumber = paginationUtils.checkPageNumber(pageNumber);
        recordsPerPage = paginationUtils.checkRecordsPerPage(recordsPerPage);

        Optional<User> patient = userService.getUser(idPatient);
        Page<Diagnosis> diagnoses = diagnosisService.findDiagnosesByPatientId(pageNumber, recordsPerPage, idPatient);

        model.addAttribute("patient", patient.get());
        model.addAttribute("page", diagnoses); //TODO fix another user database query
    }
}
