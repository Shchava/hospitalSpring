package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class DiagnosisHelpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idPrediction;

    @Column
    private String predictedName;

    @Column
    private Double predictedAccuracy;

    @Column
    @ElementCollection
    private List<String> symptoms;

    @Column
    private String comments;

    @Column
    @OneToMany(fetch = FetchType.EAGER)
    private List<DiagnosisHelpMessage> messages;

    @ManyToOne(fetch = FetchType.EAGER)
    private User patient;
}
