package ua.training.hospital.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.hospital.controller.utils.UtilsTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilsTestSuite.class,
        RegisterControllerTest.class,
        ShowPatientControllerTest.class,
        ShowDiagnosisControllerTest.class

})
public class ControllerTestSuite {
}
