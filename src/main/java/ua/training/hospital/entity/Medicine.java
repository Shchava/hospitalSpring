package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
public class Medicine extends Therapy {
    @Column
    int count;
    @Column
    LocalDateTime refill;
}
