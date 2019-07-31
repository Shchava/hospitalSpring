package ua.training.hospital.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.entity.exceptions.EmailExistsException;
import ua.training.hospital.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUser(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    @Override
    public Optional<User> registerUser(UserDTO userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address:" + userDto.getEmail());
        }
        User userToCreate =  User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .patronymic(userDto.getPatronymic())
                .email(userDto.getEmail())
                .passwordHash(encodePassword(userDto.getPassword()))
                .role(userDto.getRole())
                .build();

       return Optional.ofNullable(repository.save(userToCreate));
    }

    @Override
    public Page<User> findPatients(int pageNumber, int UsersPerPage) {
        return repository.findAllPatients(PageRequest.of(pageNumber,UsersPerPage));
    }

    @Override
    public Page<ShowUserToDoctorDTO> findPatientsToShow(int pageNumber, int UsersPerPage) {
        return repository.findPatientsForDoctorPage(PageRequest.of(pageNumber,UsersPerPage));
    }


    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    private boolean emailExists(String email){
        return repository.findByEmail(email) != null;
    }
}
