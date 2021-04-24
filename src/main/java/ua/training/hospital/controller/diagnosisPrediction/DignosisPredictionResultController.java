package ua.training.hospital.controller.diagnosisPrediction;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.service.diagnosisPrediction.DiagnosisHelpRequestService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class DignosisPredictionResultController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionResultController.class);
    private final DiagnosisHelpRequestService diagnosisHelpService;


    @RequestMapping(value = "/diagnosis-prediction/askHelp", method = RequestMethod.POST)
    public ModelAndView createHelpRequest(@ModelAttribute("prediction") @Valid PredictionResult prediction,
                                          Principal principal,
                                          ModelMap model) {

        if(Objects.nonNull(principal)) {
            Optional<DiagnosisHelpRequest> createdRequest = diagnosisHelpService.createDiagnosisHelpRequest(prediction, principal.getName());
            if(createdRequest.isPresent()) {
                return new ModelAndView("redirect:/diagnosis-prediction/help" + createdRequest.get().getIdPrediction(), model);
            }
        }
        model.addAttribute("prediction", prediction);
        return new ModelAndView("diagnoisPrediction/diagnosisPredictionResultPage", model);
    }
}
