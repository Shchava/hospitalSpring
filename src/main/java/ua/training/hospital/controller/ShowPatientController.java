package ua.training.hospital.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.controller.utils.PaginationUtils;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.exceptions.ResourceNotFoundException;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.diagnosisPrediction.DiagnosisHelpRequestService;
import ua.training.hospital.service.user.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class ShowPatientController {
    private static final Logger logger = LogManager.getLogger(ShowPatientController.class);

    @Autowired
    UserService userService;

    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    PaginationUtils paginationUtils;

    @Autowired
    DiagnosisHelpRequestService helpRequestService;

    @RequestMapping(value = "/patient{idPatient}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('DOCTOR','NURSE') or #idPatient == authentication.principal.id")
    public String defaultShowPatient(@PathVariable long idPatient,
                                     @RequestParam(defaultValue = "10") int recordsPerPage,
                                     Model model) {
        return getShowPatientPage(idPatient, 0, recordsPerPage, model);
    }

    @RequestMapping(value = "/patient{idPatient}/{pageNumber}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('DOCTOR','NURSE') or  #idPatient == authentication.principal.id")
    public String getShowPatientPage(@PathVariable long idPatient,
                                     @PathVariable int pageNumber,
                                     @RequestParam(defaultValue = "10") int recordsPerPage,
                                     Model model) {
        logger.debug("requested /patient/" + idPatient + "/" + pageNumber);
        getDiagnoses(model, pageNumber, recordsPerPage, idPatient);
        model.addAttribute("newDiagnosis", new DiagnosisDTO());
        logger.debug("returning showPatient.jsp page");
        return "showPatient";
    }


    @RequestMapping(value = "/doctor/patient{idPatient}/addDiagnosis", method = RequestMethod.POST)
    public ModelAndView addDiagnosis(@PathVariable long idPatient,
                                     @ModelAttribute("newDiagnosis") @Valid DiagnosisDTO diagnosisDTO,
                                     @RequestParam(required = false) Long helpRequestId,
                                     @RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "10") int recordsPerPage,
                                     BindingResult result,
                                     Principal principal,
                                     Model model) {
        logger.debug("requested /doctor/patient/" + idPatient + "/addDiagnosis");
        final Optional<Diagnosis> createdDiagnosis = diagnosisService.addDiagnosis(diagnosisDTO, idPatient, principal.getName());

        if (createdDiagnosis.isPresent()) {
            model.addAttribute("addedDiagnosis", true);


            if(Objects.nonNull(helpRequestId)) {

                helpRequestService.setClosed(helpRequestId,true);
                return new ModelAndView("redirect:/patient" + idPatient + "/diagnosis" + createdDiagnosis.get().getIdDiagnosis());
            }

            diagnosisDTO = new DiagnosisDTO();
            logger.debug("creation successful, page will contain plain DiagnosisDTO");
        } else {
            result.rejectValue("name", "diagnosis.creationError");
            logger.debug("creation failed, page will contain old DiagnosisDTO with rejected value");
        }
        getDiagnoses(model, pageNumber, recordsPerPage, idPatient);
        logger.debug("returning showPatient.jsp page");
        return new ModelAndView("showPatient", "newDiagnosis", diagnosisDTO);
    }

    private void getDiagnoses(Model model, int pageNumber, int recordsPerPage, long idPatient) {
        pageNumber = paginationUtils.checkPageNumber(pageNumber);
        recordsPerPage = paginationUtils.checkRecordsPerPage(recordsPerPage);

        User patient = getPatient(idPatient);
        Page<Diagnosis> diagnoses = diagnosisService.findDiagnosesByPatientId(pageNumber, recordsPerPage, idPatient);
        logger.debug("setting diagnoses page with size: " + diagnoses.getNumberOfElements() + " and user with id " + patient.getIdUser() + " to page model");
        model.addAttribute("patient", patient);
        model.addAttribute("page", diagnoses);
    }

    User getPatient(long idPatient) {
        Optional<User> patient = userService.getUser(idPatient);
        return patient.orElseThrow(ResourceNotFoundException::new);
    }
}
