package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "Treatment")
public class Procedure extends Therapy{
    @Column
    int room;

    @Column
    @ElementCollection
    List<LocalDateTime> appointmentDates;
}
