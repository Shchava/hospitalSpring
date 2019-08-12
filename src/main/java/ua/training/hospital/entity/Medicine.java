package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
public class Medicine extends Therapy {
    @Column
    int count;
    @Column
    LocalDate refill;
}
