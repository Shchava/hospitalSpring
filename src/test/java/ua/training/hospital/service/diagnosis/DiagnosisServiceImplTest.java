package ua.training.hospital.service.diagnosis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.enums.UserRole;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.service.user.UserServiceImpl;

import java.awt.print.Pageable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DiagnosisServiceImplTest {
    @Mock
    DiagnosisRepository repository;

    @Mock
    Page<Diagnosis> diagnoses;

    @InjectMocks
    DiagnosisServiceImpl service = new DiagnosisServiceImpl();



    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(repository.findDiagnosesByPatient_IdUser(any(),any())).willReturn(diagnoses);
    }

    @Test
    public void testFindDiagnosesByPatientId(){
        assertEquals(diagnoses,service.findDiagnosesByPatientId(2,5,10));
        verify(repository,times(1)).findDiagnosesByPatient_IdUser(PageRequest.of(2,5),10L);
    }
}