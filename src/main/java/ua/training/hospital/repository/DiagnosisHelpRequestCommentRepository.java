package ua.training.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.training.hospital.entity.DiagnosisHelpRequestComment;

import java.util.List;

public interface DiagnosisHelpRequestCommentRepository extends JpaRepository<DiagnosisHelpRequestComment, Long> {
    @Query("FROM DiagnosisHelpRequestComment p " +
            "WHERE p.target.idPrediction = :idDiagnosisRequest")
    List<DiagnosisHelpRequestComment> findAllByTargetIdPrediction (@Param("idDiagnosisRequest") Long idDiagnosisRequest);
}
