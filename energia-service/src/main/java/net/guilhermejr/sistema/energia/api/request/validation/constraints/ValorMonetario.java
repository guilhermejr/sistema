package net.guilhermejr.sistema.energia.api.request.validation.constraints;

import net.guilhermejr.sistema.energia.api.request.validation.ValorMonetarioValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValorMonetarioValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorMonetario {

    String message() default "Valor inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
