package ua.training.hospital.service.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.enums.UserRole;
import ua.training.hospital.entity.exceptions.EmailExistsException;
import ua.training.hospital.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
public class UserServiceImplTest {
    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserServiceImpl service = new UserServiceImpl();;

    String email = "test@email.com";
    String notExistEmail = "test2@email.com";

    User testUser = User.builder()
            .name("Testname")
            .surname("Testsurname")
            .patronymic("Testpatronymic")
            .email(email)
            .role(UserRole.DOCTOR)
            .build();

    @Before
    public void setUp() throws Exception {
        testUser = User.builder()
                .name("Testname")
                .surname("Testsurname")
                .patronymic("Testpatronymic")
                .email(email)
                .role(UserRole.DOCTOR)
                .build();


        initMocks(this);

        given(repository.findByEmail(email)).willReturn(testUser);
        given(repository.findByEmail(notExistEmail)).willReturn(null);
        given(repository.save(any())).willReturn(testUser);

        given(encoder.encode(any())).willReturn("cryptedPassword");
    }

    @Test
    public void testGetUser() {
        assertEquals(testUser,service.getUser(email).get());
        assertFalse(service.getUser(notExistEmail).isPresent());
    }

    @Test(expected = EmailExistsException.class)
    public void testRegisterExistingUser() {
        UserDTO dto = new UserDTO();
        dto.setEmail(email);
        service.registerUser(dto);
    }

    @Test
    public void testRegisterUser() {
        UserDTO dto = new UserDTO();
        dto.setName(testUser.getName());
        dto.setSurname(testUser.getSurname());
        dto.setPatronymic(testUser.getPatronymic());
        dto.setEmail(notExistEmail);
        dto.setRole(testUser.getRole());
        dto.setPassword("ff");

        testUser.setEmail(notExistEmail);
        assertEquals(testUser,service.registerUser(dto).get());
    }

    @Test
    public void testGetUserById(){
        given(repository.findByIdUser(5)).willReturn(testUser);
        assertEquals(testUser,service.getUser(5).get());
        assertFalse(service.getUser(11).isPresent());

    }
}