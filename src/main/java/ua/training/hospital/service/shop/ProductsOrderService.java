package ua.training.hospital.service.shop;

import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.shop.ProductOrder;

import java.util.Optional;

public interface ProductsOrderService {
    Optional<ProductOrder> createProductOrder(ProductOrder productOrder);
    Optional<ProductOrder> getProductOrder(long productOrderId);
}
