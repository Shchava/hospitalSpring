package ua.training.hospital.controller.utils;

import org.springframework.beans.BeanWrapperImpl;
import ua.training.hospital.controller.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String password;
    private String passwordMatch;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        password=constraintAnnotation.password();
        passwordMatch=constraintAnnotation.confirmPassword();
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        Object fieldValue = new BeanWrapperImpl(obj).getPropertyValue(password);
        Object fieldMatchValue = new BeanWrapperImpl(obj).getPropertyValue(passwordMatch);
        return fieldValue != null && fieldValue.equals(fieldMatchValue);
    }
}
