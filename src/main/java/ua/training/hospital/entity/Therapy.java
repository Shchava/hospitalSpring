package ua.training.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.EAGER)
    User assignedBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis")
    Diagnosis diagnosis;
}
