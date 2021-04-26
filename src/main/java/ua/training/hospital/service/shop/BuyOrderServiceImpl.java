package ua.training.hospital.service.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.enums.Status;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.repository.shop.BuyOrderRepository;
import ua.training.hospital.service.surgery.SurgeryServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BuyOrderServiceImpl implements BuyOrderService {
    private static final Logger logger = LogManager.getLogger(SurgeryServiceImpl.class);
    private final BuyOrderRepository buyOrderRepository;
    private final ProductsOrderService productsOrderService;
    private final UserRepository userRepository;

    @Override
    public Optional<BuyOrder> createBuyOrder(BuyOrder productOrder, String userId) {
        final User owner = userRepository.findByEmail(userId);
        if (Objects.nonNull(owner)) {
            productOrder.setOwner(owner);
        }

        return createBuyOrder(productOrder);
    }

    @Override
    public Optional<BuyOrder> createBuyOrder(BuyOrder buyOrder) {

        buyOrder.setDateCreated(LocalDateTime.now());
        buyOrder.setCurrentStatus(Status.CREATED);

        List<ProductOrder> productOrders = buyOrder.getProducts();

        for (int i =0; i < productOrders.size(); i++) {
            ProductOrder productOrder = productOrders.get(i);
            Optional<ProductOrder> repProductOrder = productsOrderService.getProductOrder(productOrder.getProductOrderId());
            if (!repProductOrder.isPresent()) {
                productOrder.setProductOrderId(0);
                Optional<ProductOrder> createdProductOrder = productsOrderService.createProductOrder(productOrder);
                if (createdProductOrder.isPresent()) {
                    productOrders.set(i,(createdProductOrder.get()));
                } else {
                    return Optional.empty();
                }
            }
        }

        try {
            return Optional.of(buyOrderRepository.save(buyOrder));
        } catch (Exception exception) {
            logger.warn(exception.getMessage());
            return Optional.empty();
        }
    }
}
