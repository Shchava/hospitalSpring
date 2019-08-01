package ua.training.hospital.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.user.UserService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShowPatientControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DiagnosisService diagnosisService;


    @Mock
    private User mockUser;

    @Mock
    private Page<Diagnosis> mockDiagnosisPage;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        given(userService.getUser(Mockito.anyLong())).willReturn(Optional.of(mockUser));
        given(diagnosisService.findDiagnosesByPatientId(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyLong()))
                .willReturn(mockDiagnosisPage);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowUserPage() throws Exception {
        mvc.perform(get("/doctor/patient11"))
                .andExpect(model().attribute("patient",mockUser))
                .andExpect(model().attribute("page",mockDiagnosisPage))
                .andExpect(status().isOk());

        verify(diagnosisService,times(1)).findDiagnosesByPatientId(0,10,11);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowUserPageWithNumberOfPage() throws Exception {
        mvc.perform(get("/doctor/patient11/5"))
                .andExpect(model().attribute("patient",mockUser))
                .andExpect(model().attribute("page",mockDiagnosisPage))
                .andExpect(status().isOk());

        verify(diagnosisService,times(1)).findDiagnosesByPatientId(5,10,11);

    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowUserPageWithNumberOfPageAndRecorsdPerPage() throws Exception {
        mvc.perform(get("/doctor/patient11/5?recordsPerPage=5"))
                .andExpect(model().attribute("patient",mockUser))
                .andExpect(model().attribute("page",mockDiagnosisPage))
                .andExpect(status().isOk());
        verify(diagnosisService,times(1)).findDiagnosesByPatientId(5,5,11);
    }
}