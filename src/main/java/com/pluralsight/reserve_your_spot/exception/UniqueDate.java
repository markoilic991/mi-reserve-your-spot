package com.pluralsight.reserve_your_spot.exception;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueDateValidator.class)
public @interface UniqueDate {

    String message() default "This date is already in use!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
