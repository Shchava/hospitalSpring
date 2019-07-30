package ua.training.hospital.controller.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {
    String message() default "{password.validation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
