package ua.training.hospital.service.surgery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.dto.SurgeryDTO;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.SurgeryRepository;
import ua.training.hospital.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SurgeryServiceImpl implements SurgeryService {
    private static final Logger logger = LogManager.getLogger(SurgeryServiceImpl.class);

    @Autowired
    SurgeryRepository surgeryRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int SurgeriesPerPage, long diagnosisId) {
        logger.debug("searching for surgeries with diagnosis id " + diagnosisId + " on page " + pageNumber + " with "
                + SurgeriesPerPage + "entries on page");
        return surgeryRepository.findSurgeriesByDiagnosisId(PageRequest.of(pageNumber, SurgeriesPerPage), diagnosisId);
    }

    @Override
    public Optional<Surgery> createSurgery(SurgeryDTO dto, long diagnosisId, String doctorEmail) {
        logger.info("trying to create procedure with name" + dto.getName() + "for diagnosis with id " + diagnosisId);
        Surgery toCreate = new Surgery();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setDate(dto.getSurgeryDate());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(diagnosisRepository.getOne(diagnosisId));
        toCreate.setAssignedBy(userRepository.findByEmail(doctorEmail));

        return Optional.ofNullable(surgeryRepository.save(toCreate));
    }
    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
