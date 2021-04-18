package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class DiagnosisHelpMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDiagnosis;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Column
    private LocalDateTime date;
}
