package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.training.hospital.entity.Diagnosis;

public interface DiagnosisRepository extends PagingAndSortingRepository<Diagnosis,Long> {
    Page<Diagnosis> findDiagnosesByPatient_IdUser(Pageable page,Long patientId);
}
