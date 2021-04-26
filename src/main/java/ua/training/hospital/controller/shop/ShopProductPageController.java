package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.controller.diagnosisPrediction.DignosisPredictionController;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.DiagnosisHelpRequestComment;
import ua.training.hospital.entity.exceptions.ResourceNotFoundException;
import ua.training.hospital.entity.shop.Product;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.service.shop.ProductsService;

import java.util.Comparator;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopProductPageController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);

    private ProductsService productsService;

    @RequestMapping(value = "/shop/product/{productId}", method = RequestMethod.GET)
    public String showHelpRequest(@PathVariable long productId,
                                  Model model) {

        Optional<Product> helpRequest = productsService.getProduct(productId);

        logger.debug("requested /shop/product/");

        if (helpRequest.isPresent()) {
            Product product = helpRequest.get();
            product.setInstruction(product.getInstruction().replace("\n","<br>"));
            model.addAttribute("product", product);
            model.addAttribute("order", ProductOrder.builder().product(product).build());
        } else {
            logger.debug("can't find product with such id");
            throw new ResourceNotFoundException();
        }

        logger.debug("returning shop/showProduct.jsp page");
        return "shop/showProduct";
    }
}
