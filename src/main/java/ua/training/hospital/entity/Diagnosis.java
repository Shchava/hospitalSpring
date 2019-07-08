package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
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

    @Column
    String name;

    @Column
    String description;

    @ManyToOne
    User patient;

    @ManyToOne
    User doctor;

    @ManyToMany
    List<Medicament> assignedMedicament;

    @ManyToMany
    List<Operation> assignedOperations;

    @ManyToMany
    List<Procedure> assignedProcedures;
}
