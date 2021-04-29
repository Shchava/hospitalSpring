package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.entity.exceptions.ResourceNotFoundException;
import ua.training.hospital.entity.shop.Cart;
import ua.training.hospital.service.shop.BuyOrderService;
import ua.training.hospital.service.shop.CartService;

import java.security.Principal;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopOrdersController {
    BuyOrderService orderService;
//    Order cartService;

    @RequestMapping(value = "/shop/orders", method = RequestMethod.GET)
    public String getOrders(Principal principal,
                            Model model) {

        model.addAttribute("orders", orderService.findBuyOrders(principal.getName()));

        return "shop/shopOrders";
    }
}
