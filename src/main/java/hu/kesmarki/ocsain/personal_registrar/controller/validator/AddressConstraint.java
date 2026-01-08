package hu.kesmarki.ocsain.personal_registrar.controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddressConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddressConstraint {
    String message() default "Invalid addresses";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
