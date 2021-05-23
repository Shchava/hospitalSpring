package ua.training.hospital.controller.diagnosisPrediction;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.hospital.controller.PatientListController;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.service.diagnosisPrediction.DiagnosisHelpRequestService;
import ua.training.hospital.service.user.UserService;

import java.security.Principal;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class DiagnosisPredictionHelpListController {
    private static final Logger logger = LogManager.getLogger(PatientListController.class);

    private DiagnosisHelpRequestService helpRequestService;
    private UserService userService;

    @RequestMapping(value = "/diagnosis-prediction/help/list/{pageNumber}", method = RequestMethod.GET)
    public String getDiagnosisPredictionHelpList(@PathVariable(required = false) int pageNumber,
                                                 @RequestParam(defaultValue = "10") int recordsPerPage,
                                                 Principal principal,
                                                 Model model) {

        logger.debug("Requested /diagnosis-prediction/help/list");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Page<DiagnosisHelpRequest> page;

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR") || a.getAuthority().equals("ROLE_NURSE"))) {
            page = helpRequestService.getAllHelpRequests(pageNumber, recordsPerPage);
        } else {
            page = helpRequestService.getHelpRequestsOfUser(principal.getName(), pageNumber, recordsPerPage);
        }

        model.addAttribute("page", page);

        logger.debug("Returning diagnoisPrediction/diagnosisHelpRequestList.jsp page to user");
        return "diagnoisPrediction/diagnosisHelpRequestList";
    }

}
