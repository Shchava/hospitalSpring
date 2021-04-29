package ua.training.hospital.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.BuyOrder;

import java.util.List;

public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
    List<BuyOrder> getBuyOrdersByOwnerEmail (String ownerEmail);
}
