package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
//@EqualsAndHashCode

@Entity
public class Medicament extends Therapy {
   // @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    long idMedicament;
}
