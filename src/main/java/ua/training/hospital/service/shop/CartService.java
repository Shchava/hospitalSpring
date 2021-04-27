package ua.training.hospital.service.shop;

import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.Cart;
import ua.training.hospital.entity.shop.Product;
import ua.training.hospital.entity.shop.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Optional<Cart> getCart (long cartId);
    Optional<Cart> getCart (String userId);
    Optional<Cart> createCart (String userId);
    Optional<Cart> createCartOrAddItemToExistingCart (ProductOrder newProduct, String userId);
}
