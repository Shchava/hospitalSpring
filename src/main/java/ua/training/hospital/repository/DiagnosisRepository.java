package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.training.hospital.entity.Diagnosis;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface DiagnosisRepository extends PagingAndSortingRepository<Diagnosis, Long> {
    Page<Diagnosis> findDiagnosesByPatient_IdUser(Pageable page, Long patientId);

    //    @Modifying
//    @Query(value = "INSERT INTO diagnosis(assigned,cured,description,name,doctor_id_user,patient_id_user) " +
//            "SELECT :diagnos."
//            ,nativeQuery = true)

    @Modifying
    @Query(value = "INSERT INTO diagnosis(name,description,assigned,patient_id_user,doctor_id_user) " +
            "SELECT :name,:description,:assigned,:patientId, hospital.user.id_user " +
            "FROM hospital.user " +
            "WHERE hospital.user.email=:doctorEmail"
            , nativeQuery = true)
    int addDiagnosis(
            @Param("name") String name,
            @Param("description") String description,
            @Param("assigned") LocalDateTime assigned,
            @Param("patientId") long patientId,
            @Param("doctorEmail") String doctorEmail);
}
