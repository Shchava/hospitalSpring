package ua.training.hospital.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUser(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    @Override
    public Optional<User> getUser(long id) {
        return Optional.ofNullable(repository.findByIdUser(id));
    }

    @Override
    @Transactional
    public Optional<User> registerUser(UserDTO userDto) {
        if (emailExists(userDto.getEmail())) {
            logger.info("tried to register user with existing email, throwing EmailExistsException");
            throw new EmailExistsException("There is an account with that email address:" + userDto.getEmail());
        }
        logger.info("trying to create user with email" + userDto.getEmail());
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
        logger.debug("searching for patients and showing them as User from page " + pageNumber + " with "
                + UsersPerPage + "entries on page");
        return repository.findAllPatients(PageRequest.of(pageNumber,UsersPerPage));
    }

    @Override
    public Page<ShowUserToDoctorDTO> findPatientsToShow(int pageNumber, int UsersPerPage) {
        logger.debug("searching for patients and showing them as ShowUserToDoctorDTO from page " + pageNumber + " with "
                + UsersPerPage + "entries on page");
        return repository.findPatientsForDoctorPage(PageRequest.of(pageNumber,UsersPerPage));
    }


    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    private boolean emailExists(String email){
        return repository.findByEmail(email) != null;
    }
}
