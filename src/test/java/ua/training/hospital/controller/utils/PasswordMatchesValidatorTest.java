package ua.training.hospital.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordMatchesValidatorTest {
    @Autowired
    private Validator validator;

    @Before
    public void setUp(){

    }

    @Test
    public void testMatchingPasswords() {
        testPasswordMatchesClass obj = new testPasswordMatchesClass("123","123");

        Set<ConstraintViolation<testPasswordMatchesClass>> constraintViolations =
                validator.validate(obj);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testNotMatchingPasswords() {
        testPasswordMatchesClass obj = new testPasswordMatchesClass("1234","123");

        Set<ConstraintViolation<testPasswordMatchesClass>> constraintViolations =
                validator.validate(obj);

        assertEquals(1, constraintViolations.size());
    }
}
@Getter
@Setter
@AllArgsConstructor
@PasswordMatches(password = "password",confirmPassword = "confirmPassword")
class testPasswordMatchesClass{
    String password;
    String confirmPassword;
}