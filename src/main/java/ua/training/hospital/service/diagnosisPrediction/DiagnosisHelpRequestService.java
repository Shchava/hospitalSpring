package ua.training.hospital.service.diagnosisPrediction;

import org.springframework.data.domain.Page;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;

import java.util.Optional;

public interface DiagnosisHelpRequestService {
    Optional<DiagnosisHelpRequest> createDiagnosisHelpRequest(PredictionResult prediction, String patientId);
    Optional<DiagnosisHelpRequest> getHelpRequest (long idHelpRequest);
    Page<DiagnosisHelpRequest> getAllHelpRequests(int pageNumber, int requestsPerPage);
    Page<DiagnosisHelpRequest> getHelpRequestsOfUser(String userEmail, int pageNumber, int requestsPerPage);
}
