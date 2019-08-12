package ua.training.hospital.service.procedure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.dto.ProcedureDTO;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.ProcedureRepository;
import ua.training.hospital.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProcedureServiceImpl implements ProcedureService {
    private static final Logger logger = LogManager.getLogger(ProcedureServiceImpl.class);

    @Autowired
    ProcedureRepository repository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Procedure> findProceduresByDiagnosisId(int pageNumber, int ProcedurePerPage, long diagnosisId) {
        logger.debug("searching for procedures with diagnosis id " + diagnosisId + " on page " + pageNumber + " with "
                + ProcedurePerPage + "entries on page");
        return repository.findProceduresByDiagnosisId(PageRequest.of(pageNumber, ProcedurePerPage), diagnosisId);
    }

    @Override
    @Transactional
    public Optional<Procedure> createProcedure(ProcedureDTO dto, long diagnosisId, String doctorEmail) {
        logger.info("trying to create procedure with name" + dto.getName() + "for diagnosis with id " + diagnosisId);
        Procedure toCreate = new Procedure();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setRoom(dto.getRoom());
        toCreate.setAppointmentDates(dto.getAppointmentDates());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(diagnosisRepository.getOne(diagnosisId));
        toCreate.setAssignedBy(userRepository.findByEmail(doctorEmail));


        return Optional.ofNullable(repository.save(toCreate));
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
