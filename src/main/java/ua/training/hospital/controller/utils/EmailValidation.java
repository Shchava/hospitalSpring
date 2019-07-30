package ua.training.hospital.controller.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailValidation {
    String message() default "{email.validation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
