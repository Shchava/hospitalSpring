package ua.training.hospital.service.diagnosisPrediction;

import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;

import java.util.Optional;

public interface DiagnosisHelpRequestService {
    Optional<DiagnosisHelpRequest> createDiagnosisHelpRequest(PredictionResult prediction, String patientId);
}
