package ua.training.hospital.controller.diagnosisPrediction;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class DiagnosisPredictionHelpController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionResultController.class);


    @RequestMapping(value = "/diagnosis-prediction/help", method = RequestMethod.GET) //todo: map to better
    public String defaultShowPatient(Model model) {

        logger.debug("requested /diagnosis-prediction/help");

        logger.debug("returning dignosisPrediction/diagnosisHelpRequest.jsp page");
        return "diagnoisPrediction/diagnosisHelpRequest";
    }
}
