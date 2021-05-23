package ua.training.hospital.service.diagnosis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.service.diagnosisPrediction.DiagnosisHelpRequestService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private static final Logger logger = LogManager.getLogger(DiagnosisServiceImpl.class);

    @Autowired
    DiagnosisRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiagnosisHelpRequestService diagnosisHelpRequestService;

    @Override
    public Page<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId) {
        logger.debug("searching for diagnoses with patient id " + patientId + " on page " + pageNumber + " with "
                + DiagnosesPerPage + "entries on page");
        return repository.findDiagnosesByPatient_IdUser(PageRequest.of(pageNumber, DiagnosesPerPage), patientId);
    }

    @Override
    @Transactional
    public Optional<Diagnosis> addDiagnosis(DiagnosisDTO dto, long patientId, String doctorEmail) {
        logger.info("trying to create diagnosis with name" + dto.getName() + "for user with id " + patientId);

        Optional<User> patient = userRepository.findById(patientId);

        if (!patient.isPresent()) {
            return Optional.empty();
        }

        User doctor = userRepository.findByEmail(doctorEmail);

        if (Objects.isNull(doctor)) {
            return Optional.empty();
        }

        return Optional.of(
                repository.save(Diagnosis
                        .builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .assigned(getCurrentTime())
                        .patient(patient.get())
                        .doctor(doctor)
                        .build())
        );
    }

    @Override
    public Optional<Diagnosis> addDiagnosis(DiagnosisDTO dto, long patientId, String doctorEmail, long causingHelpRequestId) {
        Optional<DiagnosisHelpRequest> helpRequest = diagnosisHelpRequestService.getHelpRequest(causingHelpRequestId);
        if(!helpRequest.isPresent()) {
            return Optional.empty();
        }
        logger.info("trying to create diagnosis with name" + dto.getName() + "for user with id " + patientId);

        Optional<User> patient = userRepository.findById(patientId);

        if (!patient.isPresent()) {
            return Optional.empty();
        }

        User doctor = userRepository.findByEmail(doctorEmail);

        if (Objects.isNull(doctor)) {
            return Optional.empty();
        }

        return Optional.of(
                repository.save(Diagnosis
                        .builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .assigned(getCurrentTime())
                        .patient(patient.get())
                        .doctor(doctor)
                        .causingHelpRequest(helpRequest.get())
                        .build())
        );
    }

    @Override
    public Optional<Diagnosis> getDiagnosis(long idDiagnosis) {
        logger.debug("searching for diagnosis with id: " + idDiagnosis);
        return repository.findById(idDiagnosis);
    }

    @Transactional
    public boolean closeDiagnosis(long idDiagnosis) {
        logger.info("trying to close diagnosis with id: " + idDiagnosis);
        return repository.closeDiagnosis(idDiagnosis, getCurrentTime()) == 1;
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
