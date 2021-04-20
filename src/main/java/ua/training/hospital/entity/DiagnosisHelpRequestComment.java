package ua.training.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class DiagnosisHelpRequestComment {
    public static final int MAX_COMMENT_SIZE = 2500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column
    @Size(max = MAX_COMMENT_SIZE)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Column
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private DiagnosisHelpRequest target;
}
