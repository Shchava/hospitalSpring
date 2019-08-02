package ua.training.hospital.service.medicine;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.Medicine;

public interface MedicineService {
    Page<Medicine> findMedicineByDiagnosisId(int pageNumber, int UsersPerPage, long diagnosisId);
}
