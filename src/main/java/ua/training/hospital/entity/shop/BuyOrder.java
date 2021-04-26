package ua.training.hospital.entity.shop;

import lombok.*;
import ua.training.hospital.controller.utils.EmailValidation;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.enums.DeliveryMethod;
import ua.training.hospital.entity.enums.PaymentMethod;
import ua.training.hospital.entity.enums.Status;
import ua.training.hospital.entity.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

@Entity
public class BuyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToMany(cascade = { CascadeType.ALL})
    private List<ProductOrder> products;

    @Column
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @NotNull
    @NotEmpty(message = "{field.empty}")
    String name;

    @Column
    @Size(max = 600)
    private String address;

    @Column
    @Size(max = 10)
    @NotEmpty(message = "{field.empty}")
    private String phoneNumber;

    @Column
//    @EmailValidation
    @NotEmpty(message = "{field.empty}")
    @Size(max = 60)
    private String email;

    @Column
    @NotNull(message = "{field.empty}")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column
    @NotNull(message = "{field.empty}")
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @Column
    @Enumerated(EnumType.STRING)
    private Status currentStatus;

    @Column
    @Size(max = 1500)
    private String comment;

    @Column
    private boolean notReceiveCall;
}
