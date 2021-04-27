package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.training.hospital.controller.diagnosisPrediction.DignosisPredictionController;
import ua.training.hospital.entity.exceptions.ResourceNotFoundException;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.Cart;
import ua.training.hospital.entity.shop.Product;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.service.shop.CartService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopCartPageController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);
    private final CartService cartService;


    @RequestMapping(value = "/shop/cart", method = RequestMethod.GET)
    public String defaultShowCart(Principal principal,
                                  Model model) {

        logger.debug("requested /shop");
        Cart getCart = null;

        Optional<Cart> cart = cartService.getCart(principal.getName());
        if (cart.isPresent()) {
            getCart = cart.get();
        } else {
            Optional<Cart> newCart = cartService.createCart(principal.getName());
            if (newCart.isPresent()) {
                getCart = newCart.get();
            } else {
                throw new ResourceNotFoundException();
            }
        }

        model.addAttribute("cart", getCart);

        model.addAttribute("totalPrice", getTotalPrice(getCart));

        logger.debug("returning shop/shopCartPage.jsp page");
        return "shop/shopCartPage";
    }

    @RequestMapping(value = "/shop/addToCart", method = RequestMethod.POST)
    public String register(Model model,
                           @ModelAttribute("order") ProductOrder order,
                           Principal principal) {

        Optional<Cart> cart = cartService.createCartOrAddItemToExistingCart(order, principal.getName());

        if (cart.isPresent()) {
            model.addAttribute("cart", cart.get());
            model.addAttribute("totalPrice", getTotalPrice(cart.get()));
            return "shop/shopCartPage";
        }

        return "shop/showProduct";
    }

    private int getTotalPrice(Cart cart) {
        return cart.getProducts().stream()
                .mapToInt(request -> request.getCount() * request.getProduct().getPrice())
                .sum();
    }
}
