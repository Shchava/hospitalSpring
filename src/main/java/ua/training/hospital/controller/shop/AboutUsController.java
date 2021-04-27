package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.hospital.controller.diagnosisPrediction.DignosisPredictionController;
import ua.training.hospital.entity.shop.Product;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class AboutUsController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);

    @RequestMapping(value = "/shop/about", method = RequestMethod.GET)
    public String about() {

        logger.debug("requested /shop/about");


        logger.debug("returning shop/about.jsp page");
        return "shop/shopAboutPage";
    }
}
