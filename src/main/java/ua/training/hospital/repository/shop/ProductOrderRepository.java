package ua.training.hospital.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
