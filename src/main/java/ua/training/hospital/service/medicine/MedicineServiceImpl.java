package ua.training.hospital.service.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.repository.MedicineRepository;

@Service
public class MedicineServiceImpl implements MedicineService{
    @Autowired
    MedicineRepository repository;

    @Override
    public Page<Medicine> findMedicineByDiagnosisId(int pageNumber, int UsersPerPage, long diagnosisId) {
        return repository.findMedicineByDiagnosisId(PageRequest.of(pageNumber, UsersPerPage), diagnosisId);
    }
}
