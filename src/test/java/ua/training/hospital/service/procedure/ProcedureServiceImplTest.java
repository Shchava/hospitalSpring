package ua.training.hospital.service.procedure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.controller.dto.ProcedureDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.ProcedureRepository;
import ua.training.hospital.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.hospital.TestUtils.assertTimeIsBetween;

public class ProcedureServiceImplTest {
    @Mock
    ProcedureRepository repository;

    @Mock
    DiagnosisRepository diagnosisRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Procedure procedure;

    @Mock
    Page<Procedure> procedures;

    @InjectMocks
    ProcedureServiceImpl service = new ProcedureServiceImpl();

    private String doctorEmail = "test@email.com";
    private ProcedureDTO dto;

    @Captor
    ArgumentCaptor<Procedure> procedureCaptor;

    @Before
    public void setUp() throws Exception {
        dto = new ProcedureDTO();
        dto.setName("Test name");
        dto.setDescription("Test description");
        dto.setRoom(802);
        dto.setAppointmentDates(Arrays.asList(
                LocalDateTime.of(2019,9,3,12,30),
                LocalDateTime.of(2019,9,5,12,0),
                LocalDateTime.of(2019,9,7,12,30)
        ));

        initMocks(this);
        given(repository.findProceduresByDiagnosisId(any(), anyLong())).willReturn(procedures);

        given(repository.save(any())).willReturn(procedure);
        given(diagnosisRepository.getOne(7L)).willReturn(mock(Diagnosis.class));
        given(userRepository.findByEmail(doctorEmail)).willReturn(mock(User.class));
    }

    @Test
    public void testFindProceduresByDiagnosisId() {
        assertEquals(procedures, service.findProceduresByDiagnosisId(2, 5, 10));
        verify(repository, times(1)).findProceduresByDiagnosisId(PageRequest.of(2, 5), 10L);
    }

    @Test
    public void addProcedure() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.createProcedure(dto, 7, doctorEmail).isPresent());
        LocalDateTime after = LocalDateTime.now();

        verify(repository, times(1)).save(procedureCaptor.capture());

        assertEquals(dto.getName(),procedureCaptor.getValue().getName());
        assertTimeIsBetween(procedureCaptor.getValue().getAssigned(), before, after);
    }
}