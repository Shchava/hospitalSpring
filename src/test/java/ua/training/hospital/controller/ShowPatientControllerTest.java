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
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.user.UserService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    DiagnosisDTO newDiagnosis = new DiagnosisDTO();

    @Before
    public void setUp(){
        newDiagnosis.setName("testName");
        newDiagnosis.setDescription("testDescription");

        MockitoAnnotations.initMocks(this);
        given(userService.getUser(Mockito.anyLong())).willReturn(Optional.of(mockUser));
        given(diagnosisService.findDiagnosesByPatientId(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyLong()))
                .willReturn(mockDiagnosisPage);
        given(diagnosisService.addDiagnosis(Mockito.any(),Mockito.anyLong(),Mockito.anyString()))
                .willReturn(Optional.of(new Diagnosis()));
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowUserPage() throws Exception {
        mvc.perform(get("/patient11"))
                .andExpect(model().attribute("patient",mockUser))
                .andExpect(model().attribute("page",mockDiagnosisPage))
                .andExpect(status().isOk());

        verify(diagnosisService,times(1)).findDiagnosesByPatientId(0,10,11);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowUserPageWithNumberOfPage() throws Exception {
        mvc.perform(get("/patient11/5"))
                .andExpect(model().attribute("patient",mockUser))
                .andExpect(model().attribute("page",mockDiagnosisPage))
                .andExpect(status().isOk());

        verify(diagnosisService,times(1)).findDiagnosesByPatientId(5,10,11);

    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowUserPageWithNumberOfPageAndRecorsdPerPage() throws Exception {
        mvc.perform(get("/patient11/5?recordsPerPage=5"))
                .andExpect(model().attribute("patient",mockUser))
                .andExpect(model().attribute("page",mockDiagnosisPage))
                .andExpect(status().isOk());
        verify(diagnosisService,times(1)).findDiagnosesByPatientId(5,5,11);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testAddDiagnosis()throws Exception{
        mvc.perform(post("/doctor/patient5/addDiagnosis")
                .with(csrf())
                .flashAttr("newDiagnosis",newDiagnosis)
                .flashAttr("pageNumber",2)
                .flashAttr("recordsPerPage",5))

                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("addedDiagnosis",true))
                .andExpect(status().isOk());

        verify(diagnosisService, times(1)).addDiagnosis(eq(newDiagnosis),eq(5L),anyString());
    }
}