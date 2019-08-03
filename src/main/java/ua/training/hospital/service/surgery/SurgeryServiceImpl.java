package ua.training.hospital.service.surgery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.repository.SurgeryRepository;

@Service
public class SurgeryServiceImpl implements SurgeryService {
    @Autowired
    SurgeryRepository surgeryRepository;

    @Override
    public Page<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int SurgeriesPerPage, long diagnosisId) {
        return surgeryRepository.findSurgeriesByDiagnosisId(PageRequest.of(pageNumber, SurgeriesPerPage), diagnosisId);
    }
}
