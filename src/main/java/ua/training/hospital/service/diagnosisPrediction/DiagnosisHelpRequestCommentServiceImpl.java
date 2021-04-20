package ua.training.hospital.service.diagnosisPrediction;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.DiagnosisHelpRequestComment;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisHelpRequestCommentRepository;
import ua.training.hospital.repository.DiagnosisHelpRequestRepository;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.service.diagnosis.DiagnosisServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ua.training.hospital.entity.DiagnosisHelpRequestComment.MAX_COMMENT_SIZE;

@AllArgsConstructor
@Service
public class DiagnosisHelpRequestCommentServiceImpl implements DiagnosisHelpRequestCommentService{
    private static final Logger logger = LogManager.getLogger(DiagnosisServiceImpl.class);

    private final DiagnosisHelpRequestRepository diagnosisRequestRepository;
    private final DiagnosisHelpRequestCommentRepository commentRepository;
    private final UserRepository userRepository;



    @Override
    public Optional<DiagnosisHelpRequestComment> createComment(String comment, String userId, Long helpRequestId) {
        final User author = userRepository.findByEmail(userId);
        if(Objects.isNull(author)) {
            return Optional.empty();
        }

        final Optional<DiagnosisHelpRequest> target = diagnosisRequestRepository.findById(helpRequestId);
        if(!target.isPresent()) {
            return Optional.empty();
        }

        if(comment.length() > MAX_COMMENT_SIZE) {
            comment = comment.substring(0, MAX_COMMENT_SIZE - 1);
        }

        DiagnosisHelpRequestComment toSave = DiagnosisHelpRequestComment.builder()
                .author(author)
                .content(comment)
                .date(LocalDateTime.now())
                .target(target.get())
                .build();

        return Optional.of(commentRepository.save(toSave));
    }

    @Override
    public List<DiagnosisHelpRequestComment> getComments(Long helpRequestId) {
        return commentRepository.findAllByTargetIdPrediction(helpRequestId);
    }
}
