package ua.training.hospital.service.shop;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.shop.Product;

import java.util.Optional;

public interface ProductsService {
    Optional<Product> getProduct (long productId);
    Page<Product> getAllProducts(int pageNumber, int requestsPerPage);
    Page<Product> getAllProductsWithTitleContaining(int pageNumber, int requestsPerPage, String nameSearch);
}
