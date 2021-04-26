package ua.training.hospital.entity.shop;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productOrderId;

    @ManyToOne
    private Product product;

    @Column
    private int count;
}
