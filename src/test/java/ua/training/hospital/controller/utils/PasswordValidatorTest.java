package ua.training.hospital.controller.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:regexp.properties"})
public class PasswordValidatorTest {
    @Autowired
    PasswordValidator validator;
    @Mock
    ConstraintValidatorContext context;

    @Test
    public void testNormalPassword() {
        assertTrue(validator.isValid("ABCedfgk1234", context));
    }

    @Test
    public void testShortPassword() {
        assertFalse(validator.isValid("Ak1", context));
    }

    @Test
    public void testPasswordWithoutLetters() {
        assertFalse(validator.isValid("1234567890", context));
    }

    @Test
    public void testPasswordWithoutNumbers() {
        assertFalse(validator.isValid("ABcdefgklmnop", context));
    }

}