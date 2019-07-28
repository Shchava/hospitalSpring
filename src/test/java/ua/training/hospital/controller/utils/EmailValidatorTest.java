package ua.training.hospital.controller.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:regexp.properties"})
public class EmailValidatorTest {
    @Autowired
    EmailValidator validator;
    @Mock
    ConstraintValidatorContext context;

    @Test
    public void testCorrectEmail() {
        assertTrue(validator.isValid("testemail@exam-ple.com", context));
    }

    @Test
    public void testIntranetAddress() {
        assertFalse(validator.isValid("testemail@com", context));
    }

    @Test
    public void testTooLongLocale() {
        assertTrue(validator.isValid("comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomc@com.com", context));
        assertFalse(validator.isValid("comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomc" +
                ".comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomc@com.com", context));
        assertFalse(validator.isValid("comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomco@com.com", context));
    }

    @Test
    public void testTooLongDomain() {
        assertTrue(validator.isValid("testemail@comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcom" +
                ".comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcom" +
                ".comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcom", context));
        assertFalse(validator.isValid("testemail@comcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomcomc.com", context));
    }

    @Test
    public void testCyrillicAddress() {
        assertFalse(validator.isValid("TestЕмайл@example.com", context));
    }

    @Test
    public void WrongFormatAddress() {
        assertFalse(validator.isValid("address", context));
    }
}