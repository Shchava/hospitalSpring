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
import ua.training.hospital.service.shop.ProductsService;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopPageController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);

    private ProductsService productsService;

    @RequestMapping(value = "/shop/{pageNumber}", method = RequestMethod.GET)
    public String defaultShowPatient(@PathVariable(required = false) int pageNumber,
                                     @RequestParam(defaultValue = "10") int recordsPerPage,
                                     Model model) {

        logger.debug("requested /shop");

        Page<Product> page = productsService.getAllProducts(pageNumber, recordsPerPage);
        model.addAttribute("page", page);

        logger.debug("returning shop/shopPage.jsp page");
        return "shop/shopPage";
    }
}
