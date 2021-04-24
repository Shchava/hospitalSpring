package ua.training.hospital.entity.shop;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String manufacturer;

    @Lob
    @Column(length=6000)
    private String instruction;

    @Column
    private int price;

    @Column
    private String pictureUrl;
}
