package ua.training.hospital.service.procedure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.repository.ProcedureRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProcedureServiceImplTest {
    @Mock
    ProcedureRepository repository;

    @Mock
    Page<Procedure> procedures;

    @InjectMocks
    ProcedureServiceImpl service = new ProcedureServiceImpl();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(repository.findProceduresByDiagnosisId(any(), anyLong())).willReturn(procedures);
    }

    @Test
    public void testFindDiagnosesByPatientId() {
        assertEquals(procedures, service.findProceduresByDiagnosisId(2, 5, 10));
        verify(repository, times(1)).findProceduresByDiagnosisId(PageRequest.of(2, 5), 10L);
    }
}