package ua.training.hospital.service.medicine;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.controller.dto.MedicineDTO;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.DiagnosisRepository;
import ua.training.hospital.repository.MedicineRepository;
import ua.training.hospital.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.hospital.TestUtils.assertTimeIsBetween;

public class MedicineServiceImplTest {
    @Mock
    MedicineRepository repository;

    @Mock
    DiagnosisRepository diagnosisRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Page<Medicine> medicines;

    @Mock
    Medicine medicine;

    @InjectMocks
    MedicineServiceImpl service = new MedicineServiceImpl();
    String doctorEmail = "test@email.com";
    MedicineDTO dto;

    @Captor
    ArgumentCaptor<Medicine> medicineCaptor;

    @Before
    public void setUp() throws Exception {
        dto = new MedicineDTO();
        dto.setName("Test name");
        dto.setDescription("Test description");
        dto.setCount(12);
        dto.setRefill(LocalDate.of(2019,9,12));

        initMocks(this);

        given(repository.findMedicineByDiagnosisId(any(), anyLong())).willReturn(medicines);
        given(repository.save(any())).willReturn(medicine);
        given(diagnosisRepository.getOne(7L)).willReturn(mock(Diagnosis.class));
        given(userRepository.findByEmail(doctorEmail)).willReturn(mock(User.class));
    }

    @Test
    public void testFindMedicineByDiagnosisId() {
        assertEquals(medicines, service.findMedicineByDiagnosisId(2, 5, 10));
        verify(repository, times(1)).findMedicineByDiagnosisId(PageRequest.of(2, 5), 10L);
    }

    @Test
    public void addMedicine() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.createMedicine(dto, 7, doctorEmail).isPresent());
        LocalDateTime after = LocalDateTime.now();

        verify(repository, times(1)).save(medicineCaptor.capture());

        assertEquals(dto.getName(),medicineCaptor.getValue().getName());
        assertTimeIsBetween(medicineCaptor.getValue().getAssigned(), before, after);
    }
}