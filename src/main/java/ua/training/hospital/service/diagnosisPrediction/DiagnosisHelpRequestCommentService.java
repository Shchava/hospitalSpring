package ua.training.hospital.service.diagnosisPrediction;

import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.DiagnosisHelpRequestComment;

import java.util.Optional;

public interface DiagnosisHelpRequestCommentService {
    Optional<DiagnosisHelpRequestComment> createComment(String comment, String userId, Long helpRequestId);
}
