package ua.training.hospital.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.Cart;

import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    Optional<Cart> findFirstByOwnerEmail (String userEmail);
}
