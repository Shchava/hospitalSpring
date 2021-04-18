package ua.training.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.DiagnosisHelpMessage;

public interface DiagnosisHelpMessageRepository extends JpaRepository<DiagnosisHelpMessage, Long> {

}
