package ua.training.hospital.service.surgery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.repository.SurgeryRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class SurgeryServiceImplTest {
    @Mock
    SurgeryRepository repository;

    @Mock
    Page<Surgery> procedures;

    @InjectMocks
    SurgeryServiceImpl service = new SurgeryServiceImpl();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(repository.findSurgeriesByDiagnosisId(any(), anyLong())).willReturn(procedures);
    }

    @Test
    public void testFindProceduresByDiagnosisId() {
        assertEquals(procedures, service.findSurgeriesByDiagnosisId(2, 5, 10));
        verify(repository, times(1)).findSurgeriesByDiagnosisId(PageRequest.of(2, 5), 10L);
    }
}