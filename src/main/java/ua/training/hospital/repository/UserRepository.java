package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByEmail(String email);

    User findByIdUser(long id);

    @Query("SELECT u FROM User u " +
            " WHERE u.role='PATIENT'")
    Page<User> findAllPatients(Pageable pageable);

    @Query(value = "select new ua.training.hospital.entity.dto.ShowUserToDoctorDTO(u.idUser,u.name,u.surname,u.patronymic,u.email,diag.name) " +
            "from User as u " +
            "LEFT JOIN u.myDiagnoses diag " +
            "where (diag.assigned) in (" +
            "select MAX(diag2.assigned) " +
            "from u.myDiagnoses as diag2 " +
            "GROUP BY diag2.patient) " +
            "OR diag.patient is null " +
            "AND u.role = 'PATIENT'")
    Page<ShowUserToDoctorDTO> findPatientsForDoctorPage(Pageable pageable);
}
