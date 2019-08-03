package ua.training.hospital.service.medicine;

import org.springframework.data.domain.Page;
import ua.training.hospital.controller.dto.MedicineDTO;
import ua.training.hospital.entity.Medicine;

import java.util.Optional;

public interface MedicineService {
    Page<Medicine> findMedicineByDiagnosisId(int pageNumber, int MedicinePerPage, long diagnosisId);
    Optional<Medicine> createMedicine(MedicineDTO dto, long diagnosisId, String doctorEmail);
}
