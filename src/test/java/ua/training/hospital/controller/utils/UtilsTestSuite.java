package ua.training.hospital.controller.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EmailValidatorTest.class,
        NameValidatorTest.class,
        PasswordMatchesValidatorTest.class,
        PasswordValidatorTest.class,
        PaginationUtilsTest.class
})
public class UtilsTestSuite {
}
