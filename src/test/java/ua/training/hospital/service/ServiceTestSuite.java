package ua.training.hospital.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.hospital.service.diagnosis.DiagnosisServiceImplTest;
import ua.training.hospital.service.medicine.MedicineServiceImplTest;
import ua.training.hospital.service.surgery.SurgeryServiceImplTest;
import ua.training.hospital.service.user.UserServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceImplTest.class,
        DiagnosisServiceImplTest.class,
        MedicineServiceImplTest.class,
        SurgeryServiceImplTest.class
})
public class ServiceTestSuite {
}
