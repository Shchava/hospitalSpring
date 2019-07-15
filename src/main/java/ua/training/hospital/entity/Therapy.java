package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode

@MappedSuperclass
public abstract class Therapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idTherapy;

    @Column(nullable = false)
    @NotNull
    String name;

    @Column
    String description;

    @Column
    LocalDateTime assigned;

    @ManyToOne
    User assignedBy;
}
