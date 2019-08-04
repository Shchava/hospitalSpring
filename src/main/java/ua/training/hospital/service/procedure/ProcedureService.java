package ua.training.hospital.service.procedure;

import org.springframework.data.domain.Page;
import ua.training.hospital.controller.dto.ProcedureDTO;
import ua.training.hospital.entity.Procedure;

import java.util.Optional;

public interface ProcedureService {
    Page<Procedure> findProceduresByDiagnosisId(int pageNumber, int ProcedurePerPage, long diagnosisId);
    Optional<Procedure> createMedicine(ProcedureDTO dto, long diagnosisId, String doctorEmail);
}
