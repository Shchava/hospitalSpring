package ua.training.hospital.service.shop;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.repository.shop.ProductOrderRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductsOrderServiceImpl implements ProductsOrderService{
    private final ProductOrderRepository productOrderRepository;

    @Override
    public Optional<ProductOrder> createProductOrder(ProductOrder productOrder) {
        return Optional.of(productOrderRepository.save(productOrder));
    }

    @Override
    public Optional<ProductOrder> getProductOrder(long productOrderId) {
        return productOrderRepository.findById(productOrderId);
    }
}
