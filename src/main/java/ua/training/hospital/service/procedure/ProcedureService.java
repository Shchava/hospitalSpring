package ua.training.hospital.service.procedure;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.Procedure;

public interface ProcedureService {
    Page<Procedure> findProceduresByDiagnosisId(int pageNumber, int ProcedurePerPage, long diagnosisId);
}
