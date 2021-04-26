package ua.training.hospital.service.shop;

import ua.training.hospital.entity.shop.BuyOrder;

import java.util.Optional;

public interface BuyOrderService {
    Optional<BuyOrder> createBuyOrder(BuyOrder productOrder, String userId);
    Optional<BuyOrder> createBuyOrder(BuyOrder productOrder);
}
