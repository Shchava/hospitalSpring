package ua.training.hospital.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime created;

    @Column
    @OneToMany(mappedBy = "target", fetch = FetchType.EAGER)
    private List<DiagnosisHelpRequestComment> messages;

    @ManyToOne(fetch = FetchType.EAGER)
    private User patient;

    @Column
    private Boolean closed;
}
