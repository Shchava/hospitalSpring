package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    User patient;

    @ManyToOne(fetch = FetchType.EAGER)
    User doctor;

}
