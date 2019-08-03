package ua.training.hospital.service.diagnosis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.repository.DiagnosisRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.hospital.TestUtils.assertTimeIsBetween;

public class DiagnosisServiceImplTest {
    @Mock
    DiagnosisRepository repository;

    @Mock
    Page<Diagnosis> diagnoses;

    @Mock Diagnosis diagnosis;

    @InjectMocks
    DiagnosisServiceImpl service = new DiagnosisServiceImpl();

    DiagnosisDTO diagnosisDTO = new DiagnosisDTO(
            "test name",
            "some description");

    String doctorEmail = "test@email.com";

    @Captor
    ArgumentCaptor<LocalDateTime> timeCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(repository.findDiagnosesByPatient_IdUser(any(), any())).willReturn(diagnoses);
        given(repository.addDiagnosis(anyString(), anyString(), any(), anyLong(), eq(doctorEmail))).willReturn(1);
    }

    @Test
    public void testFindDiagnosesByPatientId() {
        assertEquals(diagnoses, service.findDiagnosesByPatientId(2, 5, 10));
        verify(repository, times(1)).findDiagnosesByPatient_IdUser(PageRequest.of(2, 5), 10L);
    }

    @Test
    public void testFindDiagnosis(){
        given(repository.findById(4L)).willReturn(Optional.of(diagnosis));
        assertEquals(diagnosis,service.getDiagnosis(4).get());

    }

    @Test
    public void addDiagnosis() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.addDiagnosis(diagnosisDTO, 7, doctorEmail));
        LocalDateTime after = LocalDateTime.now();

        verify(repository, times(1)).addDiagnosis(
                eq(diagnosisDTO.getName()),
                eq(diagnosisDTO.getDescription()),
                timeCaptor.capture(),
                eq(7L),
                eq(doctorEmail));

        assertTimeIsBetween(timeCaptor.getValue(), before, after);
    }

    @Test
    public void addDiagnosisWithWrongDoctorEmail() {
        LocalDateTime before = LocalDateTime.now();
        assertFalse(service.addDiagnosis(diagnosisDTO, 7, "wrong@email.com"));
        LocalDateTime after = LocalDateTime.now();

        verify(repository, times(1)).addDiagnosis(
                eq(diagnosisDTO.getName()),
                eq(diagnosisDTO.getDescription()),
                timeCaptor.capture(),
                eq(7L),
                eq("wrong@email.com"));
        assertTimeIsBetween(timeCaptor.getValue(), before, after);
    }


}