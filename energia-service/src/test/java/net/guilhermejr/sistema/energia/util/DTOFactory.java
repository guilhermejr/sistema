package net.guilhermejr.sistema.energia.util;

import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.api.response.TotalResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DTOFactory {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDateTime agora = LocalDateTime.now();

    public static final AcompanhamentoResponse acompanhamentoResponse = AcompanhamentoResponse
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

    public static final AcompanhamentoResponse acompanhamentoResponseAtualizar = AcompanhamentoResponse
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

    public static final AcompanhamentoRequest acompanhamentoRequest1 = AcompanhamentoRequest
            .builder()
            .inicio("19/01/2022")
            .fim("15/02/2022")
            .energiaGerada("678,4")
            .energiaInjetada(558)
            .energiaConsumidaConcessionaria(318)
            .tusd("27,69")
            .te("15,78")
            .bandeira("9,96")
            .iluminacaoPublica("1,46")
            .desconto("0")
            .build();

    public static final AcompanhamentoRequest acompanhamentoRequest2 = AcompanhamentoRequest
            .builder()
            .inicio("18/03/2022")
            .fim("15/02/2022")
            .energiaGerada("713,6")
            .energiaInjetada(558)
            .energiaConsumidaConcessionaria(318)
            .tusd("27,69")
            .te("15,78")
            .bandeira("9,96")
            .iluminacaoPublica("1,46")
            .desconto("0")
            .build();

    public static final TotalResponse totalResponse = TotalResponse
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
