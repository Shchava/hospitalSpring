package ua.training.hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.training.hospital.controller.dto.MedicineDTO;
import ua.training.hospital.controller.dto.ProcedureDTO;
import ua.training.hospital.controller.dto.SurgeryDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.medicine.MedicineService;
import ua.training.hospital.service.procedure.ProcedureService;
import ua.training.hospital.service.surgery.SurgeryService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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
public class ShowDiagnosisControllerTest {
    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper;
    private ObjectWriter ow;

    @MockBean
    private DiagnosisService diagnosisService;

    @MockBean
    private MedicineService medicineService;

    @MockBean
    private ProcedureService procedureService;

    @MockBean
    private SurgeryService surgeryService;

    @Mock
    Diagnosis mockDiagnosis;

    @Mock
    Medicine mockMedicine;

    @Mock
    Procedure mockProcedure;

    @Mock
    Surgery mockSurgery;

    MedicineDTO medicineDTO = new MedicineDTO();
    @Captor
    ArgumentCaptor<MedicineDTO> medicineDTOCaptor;

    ProcedureDTO procedureDTO = new ProcedureDTO();
    @Captor
    ArgumentCaptor<ProcedureDTO> procedureDTOCaptor;

    SurgeryDTO surgeryDTO = new SurgeryDTO();
    @Captor
    ArgumentCaptor<SurgeryDTO> surgeryDTOCaptor;

    String doctorEmail = "doctor@example.com";


    @Before
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();

        medicineDTO.setName("testMedicineName");
        medicineDTO.setCount(40);

        procedureDTO.setName("testMedicineName");
        procedureDTO.setRoom(301);

        surgeryDTO.setName("testMedicineName");
        surgeryDTO.setSurgeryDate(LocalDateTime.of(2019,8,5,12,30));

        MockitoAnnotations.initMocks(this);
        given(diagnosisService.getDiagnosis(Mockito.anyLong())).willReturn(Optional.of(mockDiagnosis));
        given(medicineService.createMedicine(any(),eq(22L),any())).willReturn(Optional.of(mockMedicine));
        given(procedureService.createProcedure(any(),eq(22L),any())).willReturn(Optional.of(mockProcedure));
        given(surgeryService.createSurgery(any(),eq(22L),any())).willReturn(Optional.of(mockSurgery));

    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testGetShowDiagnosisPage() throws Exception {
        mvc.perform(get("/doctor/patient11/diagnosis22"))
                .andExpect(model().attribute("diagnosis",mockDiagnosis))
                .andExpect(status().isOk());

        verify(diagnosisService,times(1)).getDiagnosis(22L);
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testAddMedicine()throws Exception{
        MvcResult result = mvc.perform(post("/doctor/diagnosis22/addMedicine")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(medicineDTO )))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"created\""));

        verify(medicineService, times(1)).createMedicine(medicineDTOCaptor.capture(),eq(22L),any());
        assertEquals(medicineDTO.getName(),medicineDTOCaptor.getValue().getName());
        assertEquals(medicineDTO.getCount(),medicineDTOCaptor.getValue().getCount());
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testAddProcedure()throws Exception{
        MvcResult result = mvc.perform(post("/doctor/diagnosis22/addProcedure")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(procedureDTO )))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"created\""));
        verify(procedureService,times(1)).createProcedure(procedureDTOCaptor.capture(),eq(22L),any());
        assertEquals(procedureDTO.getName(),procedureDTOCaptor.getValue().getName());
        assertEquals(procedureDTO.getRoom(),procedureDTOCaptor.getValue().getRoom());
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void testAddSurgery()throws Exception{
        MvcResult result = mvc.perform(post("/doctor/diagnosis22/addSurgery")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"testMedicineName\",\n" +
                        "  \"surgeryDate\" : \"2019-08-05T12:30:00\"}"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"created\""));
        verify(surgeryService,times(1)).createSurgery(surgeryDTOCaptor.capture(),eq(22L),any());
        assertEquals(surgeryDTO.getName(),surgeryDTOCaptor.getValue().getName());
        assertEquals(surgeryDTO.getSurgeryDate(),surgeryDTOCaptor.getValue().getSurgeryDate());
    }
}