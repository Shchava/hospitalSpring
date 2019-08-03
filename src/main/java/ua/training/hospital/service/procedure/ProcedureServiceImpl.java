package ua.training.hospital.service.procedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.repository.ProcedureRepository;

@Service
public class ProcedureServiceImpl implements ProcedureService {
    @Autowired
    ProcedureRepository repository;

    @Override
    public Page<Procedure> findProceduresByDiagnosisId(int pageNumber, int ProcedurePerPage, long diagnosisId) {
        return repository.findProceduresByDiagnosisId(PageRequest.of(pageNumber, ProcedurePerPage), diagnosisId);
    }
}
