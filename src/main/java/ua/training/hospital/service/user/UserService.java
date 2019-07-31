package ua.training.hospital.service.user;

import org.springframework.data.domain.Page;
import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
    Optional<User> registerUser(UserDTO userDto);
    Page<User> findPatients(int pageNumber, int UsersPerPage);
    Page<ShowUserToDoctorDTO> findPatientsToShow(int pageNumber, int UsersPerPage);
}
