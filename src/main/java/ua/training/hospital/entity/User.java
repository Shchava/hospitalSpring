package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUser;

    @Column
    String name;

    @Column
    String surName;

    @Column
    String email;

    @Column
    String passwordHash;

    @Column
    String role;
}
