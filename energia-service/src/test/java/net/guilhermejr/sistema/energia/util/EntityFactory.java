package net.guilhermejr.sistema.energia.util;

import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.domain.entity.Total;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntityFactory {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDateTime agora = LocalDateTime.now();

    public static final Acompanhamento acompanhamento1 = Acompanhamento
            .builder()
                .inicio(LocalDate.parse("18/11/2021", dtf))
                .fim(LocalDate.parse("20/12/2021", dtf))
                .dias(32)
                .energiaGerada(new BigDecimal("590.2"))
                .energiaInjetada(485)
                .energiaConsumidaConcessionaria(339)
                .energiaConsumidaTotal(new BigDecimal("444.2"))
                .saldoMes(146)
                .tusd(new BigDecimal("29.04"))
                .te(new BigDecimal("16.56"))
                .bandeira(new BigDecimal("10.45"))
                .iluminacaoPublica(new BigDecimal("1.12"))
                .desconto(new BigDecimal("0"))
                .valorTotal(new BigDecimal("57.17"))
                .criado(agora)
                .atualizado(agora)
            .build();

    public static final Acompanhamento acompanhamento2 = Acompanhamento
            .builder()
                .inicio(LocalDate.parse("21/12/2021", dtf))
                .fim(LocalDate.parse("18/01/2022", dtf))
                .dias(28)
                .energiaGerada(new BigDecimal("659.3"))
                .energiaInjetada(537)
                .energiaConsumidaConcessionaria(267)
                .energiaConsumidaTotal(new BigDecimal("389.3"))
                .saldoMes(270)
                .tusd(new BigDecimal("28.97"))
                .te(new BigDecimal("16.51"))
                .bandeira(new BigDecimal("10.42"))
                .iluminacaoPublica(new BigDecimal("1.46"))
                .desconto(new BigDecimal("0"))
                .valorTotal(new BigDecimal("57.36"))
                .criado(agora)
                .atualizado(agora)
            .build();

    public static final Acompanhamento acompanhamento3 = Acompanhamento
            .builder()
                .inicio(LocalDate.parse("19/01/2022", dtf))
                .fim(LocalDate.parse("15/02/2022", dtf))
                .dias(27)
                .energiaGerada(new BigDecimal("678.4"))
                .energiaInjetada(558)
                .energiaConsumidaConcessionaria(318)
                .energiaConsumidaTotal(new BigDecimal("438.4"))
                .saldoMes(240)
                .tusd(new BigDecimal("27.69"))
                .te(new BigDecimal("15.78"))
                .bandeira(new BigDecimal("9.96"))
                .iluminacaoPublica(new BigDecimal("1.46"))
                .desconto(new BigDecimal("0"))
                .valorTotal(new BigDecimal("54.89"))
                .criado(agora)
                .atualizado(agora)
            .build();

    public static final Total total = Total
            .builder()
                .id(1L)
                .energiaGerada(new BigDecimal("590.2"))
                .energiaInjetada(485)
                .energiaConsumidaConcessionaria(339)
                .energiaConsumidaTotal(new BigDecimal("444.2"))
                .saldoMes(146)
                .tusd(new BigDecimal("29.04"))
                .te(new BigDecimal("16.56"))
                .bandeira(new BigDecimal("10.45"))
                .iluminacaoPublica(new BigDecimal("1.12"))
                .desconto(new BigDecimal("0"))
                .valorTotal(new BigDecimal("57.17"))
            .build();

}
