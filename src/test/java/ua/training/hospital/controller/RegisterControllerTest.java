package ua.training.hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.training.hospital.Application;
import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.enums.UserRole;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
    UserDTO user = new UserDTO();

    @Before
    public void setUp(){
        user.setName("Testname");
        user.setSurname("Testsurname");
        user.setPatronymic("Testpatronymic");
        user.setEmail("test@email.com");
        user.setPassword("testpassword123");
        user.setConfirmPassword("testpassword123");
        user.setRole(UserRole.DOCTOR);
    }
    @Test
    public void testGetRegistrationForm() throws Exception {
        mvc.perform(get("/registration"))
                .andExpect(status().isOk());
    }

    @Test
    public void registerWithInvalidData() throws Exception {
        user.setName("'wrong name");
        user.setPassword("'pas");
        user.setEmail("wrongEmail");
        user.setSurname("'wrong surname");
        user.setPatronymic("'wrong patronymic");
        mvc.perform(post("/registration")
                .with(csrf())
                .flashAttr("user",user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "name"))
                .andExpect(model().attributeHasFieldErrors("user", "surname"))
                .andExpect(model().attributeHasFieldErrors("user", "patronymic"))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(status().isOk());
    }
}