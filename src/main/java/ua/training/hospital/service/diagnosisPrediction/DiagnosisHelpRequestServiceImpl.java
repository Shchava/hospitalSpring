package ua.training.hospital.service.diagnosisPrediction;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisHelpRequestRepository;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.service.diagnosis.DiagnosisServiceImpl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DiagnosisHelpRequestServiceImpl implements DiagnosisHelpRequestService{
    private static final Logger logger = LogManager.getLogger(DiagnosisServiceImpl.class);

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
                .created(LocalDateTime.now())
                .patient(patient)
                .build();

        return Optional.of(repository.save(toSave));
    }

    @Override
    public Optional<DiagnosisHelpRequest> getHelpRequest (long idHelpRequest) {
        logger.debug("searching for HelpRequest with id: " + idHelpRequest);
        return repository.findById(idHelpRequest);
    }

    @Override
    public boolean setClosed(long idHelpRequest, boolean closed) {
        Optional<DiagnosisHelpRequest> helpRequest = repository.findById(idHelpRequest);
        if(!helpRequest.isPresent()) {
            return false;
        } else {
            DiagnosisHelpRequest toUpdate = helpRequest.get();
            toUpdate.setClosed(closed);
            repository.save(toUpdate);
            return true;
        }
    }


    @Override
    public Page<DiagnosisHelpRequest> getAllHelpRequests(int pageNumber, int requestsPerPage) {
        logger.debug("searching for help requests from page " + pageNumber + " with " + requestsPerPage + "entries on page");
        return repository.getDiagnosisHelpRequestByClosedNull(PageRequest.of(pageNumber,requestsPerPage));
    }

    @Override
    public Page<DiagnosisHelpRequest> getHelpRequestsOfUser(String userEmail, int pageNumber, int requestsPerPage) {
        final User patient = userRepository.findByEmail(userEmail);
        if(Objects.isNull(patient)) {
            return Page.empty();
        }

        return repository.getDiagnosisHelpRequestByPatient_IdUserAndClosedIsFalse(PageRequest.of(pageNumber,requestsPerPage), patient.getIdUser());
    }
}
