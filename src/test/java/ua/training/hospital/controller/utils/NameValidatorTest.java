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
public class NameValidatorTest {
    @Autowired
    NameValidator validator;
    @Mock
    ConstraintValidatorContext context;

    @Test
    public void testNameCheckGeneral() {
        assertTrue(validator.isValid("Abcdefghiklmnopqrstvxyz", context));
        assertTrue(validator.isValid("Абвгґдеєжзиіїйклмнопрстуфхцчшщьюя", context));
    }

    @Test
    public void testNameCheckFirstLetter() {
        assertFalse(validator.isValid("абвгде", context));
        assertFalse(validator.isValid("afbcde", context));
        assertFalse(validator.isValid("'gagga", context));
        assertFalse(validator.isValid("Ибвгдж", context));
        assertFalse(validator.isValid("Ьбвгдж", context));
    }

    @Test
    public void testNameCheckInnerApostrophe() {
        assertTrue(validator.isValid("Ab'cdefgh", context));
        assertTrue(validator.isValid("Аб'вгдж", context));
    }

    @Test
    public void testNameCheckLastApostrophe() {
        assertFalse(validator.isValid("Abcdefgh'", context));
        assertFalse(validator.isValid("Абвгдж'", context));
    }

    @Test
    public void testNameCheckDoubleApostrophe() {
        assertFalse(validator.isValid("Abcd''efgh", context));
        assertFalse(validator.isValid("А''п'п", context));
    }

    @Test
    public void testNameOneLetter() {
        assertFalse(validator.isValid("Y", context));
        assertFalse(validator.isValid("Ї", context));
    }

    @Test
    public void testDoubleNameWithSpace() {
        assertTrue(validator.isValid("Abcde iklmnop", context));
        assertTrue(validator.isValid("Абвг Деєї", context));
    }

    @Test
    public void testDoubleNameWithHyphen() {
        assertTrue(validator.isValid("Abcde-iklmnop", context));
        assertTrue(validator.isValid("Абвг-Деєї", context));
    }

    @Test
    public void testTripleNameWithSpace() {
        assertTrue(validator.isValid("Abcde iklmnop Opqrs", context));
        assertTrue(validator.isValid("Абвгґде Єжзиіїйклмнопрст Уфхцчшщьюя", context));
    }

    @Test
    public void testTripleNameWithHyphen() {
        assertTrue(validator.isValid("Abcde-iklmnop Opqrs", context));
        assertTrue(validator.isValid("Абвгґде-Єжзиіїйклмнопрст-Уфхцчшщьюя", context));
    }

    @Test
    public void testMultiLanguageName() {
        assertFalse(validator.isValid("Abcбвгґ", context));
        assertFalse(validator.isValid("Абвгґде-iklmnop-Opqrs", context));
    }
}