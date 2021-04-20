package ua.training.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class DiagnosisHelpRequestComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDiagnosis;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Column
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private DiagnosisHelpRequest target;
}
