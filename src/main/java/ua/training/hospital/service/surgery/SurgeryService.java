package ua.training.hospital.service.surgery;

import org.springframework.data.domain.Page;
import ua.training.hospital.controller.dto.SurgeryDTO;
import ua.training.hospital.entity.Surgery;

import java.util.Optional;

public interface SurgeryService {
    Page<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int SurgeriesPerPage, long diagnosisId);
    Optional<Surgery> createSurgery(SurgeryDTO dto, long diagnosisId, String doctorEmail);
}
