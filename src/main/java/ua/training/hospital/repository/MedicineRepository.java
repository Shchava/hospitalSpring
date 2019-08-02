package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Medicine;

public interface MedicineRepository extends PagingAndSortingRepository<Medicine, Long> {
    @Query("SELECT m FROM Medicine m " +
            "WHERE m.diagnosis = :diagnosisId")
    Page<Medicine> findMedicineByDiagnosisId(Pageable page, @Param("diagnosisId") Long diagnosisId);
}
