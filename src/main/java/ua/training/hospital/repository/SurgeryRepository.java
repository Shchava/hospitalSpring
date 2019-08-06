package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.training.hospital.entity.Surgery;

public interface SurgeryRepository extends PagingAndSortingRepository<Surgery, Long> {
    @Query("FROM Surgery s " +
            "WHERE s.diagnosis.idDiagnosis = :diagnosisId")
    Page<Surgery> findSurgeriesByDiagnosisId(Pageable page, @Param("diagnosisId") Long diagnosisId);
}
