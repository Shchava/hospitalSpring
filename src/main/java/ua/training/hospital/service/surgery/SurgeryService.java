package ua.training.hospital.service.surgery;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.Surgery;

public interface SurgeryService {
    Page<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int SurgeriesPerPage, long diagnosisId);
}
