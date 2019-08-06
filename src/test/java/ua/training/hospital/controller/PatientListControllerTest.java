package ua.training.hospital.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.service.user.UserService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientListControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Mock
    private Page<ShowUserToDoctorDTO> mockPage;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        given(userService.findPatientsToShow(Mockito.anyInt(),Mockito.anyInt())).willReturn(mockPage);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetDoctorPage() throws Exception {
        mvc.perform(get("/doctor/page"))
                .andExpect(model().attribute("page",mockPage))
                .andExpect(status().isOk());

        verify(userService,times(1)).findPatientsToShow(0,10);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetDoctorPageWithNumberOfPage() throws Exception {
        mvc.perform(get("/doctor/page/5"))
                .andExpect(model().attribute("page",mockPage))
                .andExpect(status().isOk());

        verify(userService,times(1)).findPatientsToShow(5,10);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetDoctorPageWithNumberOfPageAndRecorsdPerPage() throws Exception {
        mvc.perform(get("/doctor/page/5?recordsPerPage=5"))
                .andExpect(model().attribute("page",mockPage))
                .andExpect(status().isOk());

        verify(userService,times(1)).findPatientsToShow(5,5);
    }
}