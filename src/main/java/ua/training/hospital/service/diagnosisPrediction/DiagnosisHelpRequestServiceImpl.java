package ua.training.hospital.service.diagnosisPrediction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisHelpRequestRepository;
import ua.training.hospital.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DiagnosisHelpRequestServiceImpl implements DiagnosisHelpRequestService{

    private final DiagnosisHelpRequestRepository repository;
    private final UserRepository userRepository;

    @Override
    public Optional<DiagnosisHelpRequest> createDiagnosisHelpRequest(PredictionResult prediction, String patientId) {
        final User patient = userRepository.findByEmail(patientId);
        if(Objects.isNull(patient)) {
            return Optional.empty();
        }

        DiagnosisHelpRequest toSave = DiagnosisHelpRequest.builder()
                .predictedName(prediction.getName())
                .predictedAccuracy(prediction.getAccuracy())
                .symptoms(prediction.getSymptoms())
                .comments(prediction.getComments())
                .patient(patient)
                .build();

        return Optional.of(repository.save(toSave));
    }
}
