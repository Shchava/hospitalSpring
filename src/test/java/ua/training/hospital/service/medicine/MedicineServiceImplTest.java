package ua.training.hospital.service.medicine;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.repository.MedicineRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class MedicineServiceImplTest {
    @Mock
    MedicineRepository repository;

    @Mock
    Page<Medicine> medicines;

    @InjectMocks
    MedicineServiceImpl service = new MedicineServiceImpl();


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(repository.findMedicineByDiagnosisId(any(), anyLong())).willReturn(medicines);
    }

    @Test
    public void testFindMedicineByDiagnosisId() {
        assertEquals(medicines, service.findMedicineByDiagnosisId(2, 5, 10));
        verify(repository, times(1)).findMedicineByDiagnosisId(PageRequest.of(2, 5), 10L);
    }
}