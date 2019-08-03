package ua.training.hospital.service.diagnosis;

import org.springframework.data.domain.Page;
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.entity.Diagnosis;

import java.util.Optional;

public interface DiagnosisService {
    Page<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId);
    boolean addDiagnosis(DiagnosisDTO dto,long patientId,String doctorEmail);
    Optional<Diagnosis> getDiagnosis(long idDiagnosis);
}
