package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.exceptions.EmailExistsException;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.service.shop.BuyOrderService;
import ua.training.hospital.service.shop.ProductsService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopBuyPageController {

    private ProductsService productsService;
    private BuyOrderService buyOrderService;

    @RequestMapping(value = "/shop/buy", method = RequestMethod.GET)
    public String getBuyPage(Model model,
                             Principal principal) {
        List<ProductOrder> requestedItems = new ArrayList<>();
        requestedItems.add(
                ProductOrder.builder()
                        .productOrderId(0)
                        .product(productsService.getProduct(1).get())
                        .count(3)
                        .build());
        requestedItems.add(
                ProductOrder.builder()
                        .product(productsService.getProduct(2).get())
                        .productOrderId(1)
                        .count(4)
                        .build());

        int totalPrice = requestedItems.stream()
                .mapToInt(request -> request.getCount() * request.getProduct().getPrice())
                .sum();

//        model.addAttribute("requestedItems", requestedItems);
        model.addAttribute("totalPrice", totalPrice);

        BuyOrder order = new BuyOrder();
        order.setProducts(requestedItems);

        order.setName("test");
        model.addAttribute("order", order);
        return "shop/shopBuyPage";
    }

    @ResponseBody
    @RequestMapping(value = "/shop/buy", method = RequestMethod.POST)
    public ModelAndView register(
            @ModelAttribute("order") @Valid BuyOrder order,
            BindingResult result,
            WebRequest request,
            Principal principal,
            Errors errors
    ) {
        if (!result.hasErrors()) {
            Optional<BuyOrder> created;
            if (Objects.nonNull(principal)) {
                created = buyOrderService.createBuyOrder(order, principal.getName());
            } else {
                created = buyOrderService.createBuyOrder(order);
            }

            if (created.isPresent()) {
                ModelAndView success = new ModelAndView("shop/shopPage");
                return success;
            } else {
                result.rejectValue("order", "registration.error");
            }
        }
        return new ModelAndView("shop/shopBuyPage", "order", order);
    }
}
