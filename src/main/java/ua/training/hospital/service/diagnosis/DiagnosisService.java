package ua.training.hospital.service.diagnosis;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.Diagnosis;

public interface DiagnosisService {
    Page<Diagnosis> findDiagnosesByPatientId(int pageNumber, int UsersPerPage, long patientId);
}
