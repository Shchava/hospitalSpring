package ua.training.hospital.service.medicine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.dto.MedicineDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.MedicineRepository;
import ua.training.hospital.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService{
    private static final Logger logger = LogManager.getLogger(MedicineServiceImpl.class);

    @Autowired
    MedicineRepository repository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Medicine> findMedicineByDiagnosisId(int pageNumber, int MedicinePerPage, long diagnosisId) {
        logger.debug("searching for medicines with diagnosis id " + diagnosisId + " on page " + pageNumber + " with "
                + MedicinePerPage + "entries on page");
        return repository.findMedicineByDiagnosisId(PageRequest.of(pageNumber, MedicinePerPage), diagnosisId);
    }

    @Override
    @Transactional
    public Optional<Medicine> createMedicine(MedicineDTO dto, long diagnosisId, String doctorEmail) {
        logger.info("trying to create medicine with name" + dto.getName() + "for diagnosis with id " + diagnosisId);
        Medicine toCreate = new Medicine();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setCount(dto.getCount());
        toCreate.setRefill(dto.getRefill());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(diagnosisRepository.getOne(diagnosisId));
        toCreate.setAssignedBy(userRepository.findByEmail(doctorEmail));

        return Optional.ofNullable(repository.save(toCreate));
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
