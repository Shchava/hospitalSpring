package ua.training.hospital.entity.shop;

import lombok.*;
import ua.training.hospital.entity.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @OneToOne(mappedBy = "cart")
    private User owner;

    @OneToMany(cascade = { CascadeType.ALL})
    private List<ProductOrder> products;
}
