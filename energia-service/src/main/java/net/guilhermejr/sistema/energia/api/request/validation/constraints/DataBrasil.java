package net.guilhermejr.sistema.energia.api.request.validation.constraints;

import net.guilhermejr.sistema.energia.api.request.validation.DataBrasilValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataBrasilValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBrasil {

    String message() default "Data inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
