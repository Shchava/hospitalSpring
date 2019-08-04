package ua.training.hospital.service.surgery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.controller.dto.SurgeryDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.SurgeryRepository;
import ua.training.hospital.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.hospital.TestUtils.assertTimeIsBetween;

public class SurgeryServiceImplTest {
    @Mock
    SurgeryRepository repository;

    @Mock
    DiagnosisRepository diagnosisRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Page<Surgery> surgeries;

    @Mock
    Surgery surgery;

    @InjectMocks
    SurgeryServiceImpl service = new SurgeryServiceImpl();

    private String doctorEmail = "test@email.com";
    private SurgeryDTO dto;

    @Captor
    ArgumentCaptor<Surgery> surgeryCaptor;

    @Before
    public void setUp() throws Exception {
        dto = new SurgeryDTO();
        dto.setName("Test name");
        dto.setDescription("Test description");
        dto.setDate(LocalDateTime.of(2019,9,5,12,0));

        initMocks(this);
        given(repository.findSurgeriesByDiagnosisId(any(), anyLong())).willReturn(surgeries);

        given(repository.save(any())).willReturn(surgery);
        given(diagnosisRepository.getOne(7L)).willReturn(mock(Diagnosis.class));
        given(userRepository.findByEmail(doctorEmail)).willReturn(mock(User.class));
    }

    @Test
    public void testFindProceduresByDiagnosisId() {
        assertEquals(surgeries, service.findSurgeriesByDiagnosisId(2, 5, 10));
        verify(repository, times(1)).findSurgeriesByDiagnosisId(PageRequest.of(2, 5), 10L);
    }

    @Test
    public void addProcedure() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.createSurgery(dto, 7, doctorEmail).isPresent());
        LocalDateTime after = LocalDateTime.now();

        verify(repository, times(1)).save(surgeryCaptor.capture());

        assertEquals(dto.getName(), surgeryCaptor.getValue().getName());
        assertTimeIsBetween(surgeryCaptor.getValue().getAssigned(), before, after);
    }
}