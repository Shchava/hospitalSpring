package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.training.hospital.entity.Procedure;

public interface ProcedureRepository extends PagingAndSortingRepository<Procedure, Long> {
    @Query("FROM Procedure p " +
            "WHERE p.diagnosis.idDiagnosis = :diagnosisId")
    Page<Procedure> findProceduresByDiagnosisId(Pageable page, @Param("diagnosisId") Long diagnosisId);
}
