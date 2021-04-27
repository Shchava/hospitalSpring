package ua.training.hospital.entity.shop;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ua.training.hospital.entity.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @OneToOne(cascade = CascadeType.ALL)
    private User owner;

    @OneToMany(cascade = { CascadeType.ALL})
    private List<ProductOrder> products;
}
