package ua.training.hospital.entity;

import lombok.*;
import ua.training.hospital.entity.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUser;

    @Column(nullable = false)
    @NotNull
    String name;

    @Column(nullable = false)
    @NotNull
    String surname;

    @Column(nullable = false)
    @NotNull
    String patronymic;

    @Column(nullable = false, unique = true)
    @NotNull
    String email;

    @Column(nullable = false)
    @NotNull
    String passwordHash;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    UserRole role;
}
