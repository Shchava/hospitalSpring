package ua.training.hospital.service.diagnosis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.repository.DiagnosisRepository;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    @Autowired
    DiagnosisRepository repository;

    @Override
    public Page<Diagnosis> findDiagnosesByPatientId(int pageNumber, int UsersPerPage, long patientId) {
        return repository.findDiagnosesByPatient_IdUser(PageRequest.of(pageNumber,UsersPerPage),patientId);
    }
}
