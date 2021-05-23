package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.DiagnosisHelpRequest;

public interface DiagnosisHelpRequestRepository extends JpaRepository<DiagnosisHelpRequest, Long> {
    Page<DiagnosisHelpRequest> getDiagnosisHelpRequestByPatient_IdUser(Pageable page, Long patientId);
}
