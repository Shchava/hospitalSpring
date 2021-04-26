package ua.training.hospital.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.BuyOrder;

public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
}
