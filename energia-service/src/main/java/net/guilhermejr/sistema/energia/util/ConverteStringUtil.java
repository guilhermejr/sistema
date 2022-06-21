package net.guilhermejr.sistema.energia.util;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.energia.exception.ExceptionDefault;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Log4j2
@Component
public class ConverteStringUtil {

    public LocalDate toLocalDate(String data) {

        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(data, parser);

    }

    public BigDecimal toBigDecimal(String valor) {

        if (valor == null) {
            valor = "0";
        }

        try {
            return new BigDecimal(valor.replace(".", "").replace(',','.'));
        } catch (Exception ex) {
            log.error("Erro ao converter String para BigDecimal");
            throw new ExceptionDefault("Erro ao converter String para BigDecimal");
        }

    }

}
