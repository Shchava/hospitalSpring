package ua.training.hospital.controller.diagnosisPrediction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.service.user.UserService;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class DignosisPredictionController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);

    @Autowired
    UserService userService;


    @RequestMapping(value = "/diagnosis-prediction", method = RequestMethod.GET)
    public String defaultShowPatient(Model model) {

        logger.debug("requested /diagnosis-prediction");

        logger.debug("returning dignosisPrediction/diagnosisPredictionPage.jsp page");
        return "diagnoisPrediction/diagnosisPredictionPage";
    }
}
