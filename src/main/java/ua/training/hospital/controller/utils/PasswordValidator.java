package ua.training.hospital.controller.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<PasswordValidation,String> {
    @Value("${pattern.password}")
    private String passwordPattern;

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches(passwordPattern);
    }

}
