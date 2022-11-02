package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prescriptionId;

    @Column
    private String name;

    @Column
    private String diagnosisName;


    @Column
    private LocalDateTime prescriptionDate;

    @Column
    long idUser;
}
