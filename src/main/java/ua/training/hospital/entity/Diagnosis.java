package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idDiagnosis;

    @Column(nullable = false)
    @NotNull
    String name;

    @Column
    String description;

    @Column
    LocalDateTime assigned;

    @Column
    LocalDateTime cured;

    @ManyToOne
    User patient;

    @ManyToOne
    User doctor;

}
