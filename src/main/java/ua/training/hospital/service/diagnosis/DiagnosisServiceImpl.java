package ua.training.hospital.service.diagnosis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.ShowPatientController;
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.repository.DiagnosisRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private static final Logger logger = LogManager.getLogger(DiagnosisServiceImpl.class);

    @Autowired
    DiagnosisRepository repository;

    @Override
    public Page<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId) {
        logger.debug("searching for diagnoses with patient id " + patientId + " on page " + pageNumber + " with "
                + DiagnosesPerPage + "entries on page");
        return repository.findDiagnosesByPatient_IdUser(PageRequest.of(pageNumber, DiagnosesPerPage), patientId);
    }

    @Override
    @Transactional
    public boolean addDiagnosis(DiagnosisDTO dto, long patientId, String doctorEmail) {
        logger.info("trying to create diagnosis with name" + dto.getName() + "for user with id " + patientId);
        return 1 == repository.addDiagnosis(
                dto.getName(),
                dto.getDescription(),
                getAssignedTime(),
                patientId,
                doctorEmail
        );
    }

    @Override
    public Optional<Diagnosis> getDiagnosis(long idDiagnosis) {
        logger.debug("searching for diagnosis with id: " + idDiagnosis);
        return repository.findById(idDiagnosis);
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
