package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
public abstract class Therapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idTherapy;

    @Column
    String name;

    @Column
    String description;

    @Column
    LocalDateTime assigned;

    @ManyToOne
    User assignedBy;
}
