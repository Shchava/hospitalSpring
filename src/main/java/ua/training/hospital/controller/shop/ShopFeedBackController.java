package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.controller.diagnosisPrediction.DignosisPredictionController;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopFeedBackController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);

    @RequestMapping(value = "/shop/feedback", method = RequestMethod.GET)
    public String about() {

        logger.debug("requested /shop/feedback");


        logger.debug("returning shop/feedback.jsp page");
        return "shop/shopFeedbackPage";
    }
}
