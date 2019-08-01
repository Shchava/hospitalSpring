package ua.training.hospital;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.hospital.controller.ControllerTestSuite;
import ua.training.hospital.service.ServiceTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ControllerTestSuite.class,
        ServiceTestSuite.class
})
public class HospitalTestSuite {
}
