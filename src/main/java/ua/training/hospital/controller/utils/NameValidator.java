package ua.training.hospital.controller.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NameValidator implements ConstraintValidator<NameValidation,String> {
    @Value("${pattern.name}")
    private String namePattern;

    @Override
    public void initialize(NameValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.matches(namePattern);
    }

}
