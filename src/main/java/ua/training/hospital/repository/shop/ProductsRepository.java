package ua.training.hospital.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
