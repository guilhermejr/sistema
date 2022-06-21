package net.guilhermejr.sistema.energia.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Retorno de acompanhamento")
public class AcompanhamentoResponse {

    @Schema(description = "Id", example = "1")
    private Long id;

    @Schema(description = "Data do início do ciclo de consumo", example = "2021-11-18")
    private LocalDate inicio;

    @Schema(description = "Data do fim do ciclo de consumo", example = "2021-12-20")
    private LocalDate fim;

    @Schema(description = "Quantos dias se passaram entre início e fim", example = "32")
    private Integer dias;

    @Schema(description = "Energia gerada", example = "653.5")
    private BigDecimal energiaGerada;

    @Schema(description = "Energia injetada", example = "485")
    private Integer energiaInjetada;

    @Schema(description = "Energia consumida", example = "339")
    private Integer energiaConsumida;

    @Schema(description = "Saldo de energia, que é calculado diminuindo a energia injetada pela energia consumida", example = "146")
    private Integer saldoMes;

    @Schema(description = "Tarifa", example = "29.04")
    private BigDecimal tusd;

    @Schema(description = "Tarifa", example = "16.56")
    private BigDecimal te;

    @Schema(description = "Bandeira tarifária", example = "10.45")
    private BigDecimal bandeira;

    @Schema(description = "Iluminação pública", example = "1.12")
    private BigDecimal iluminacaoPublica;

    @Schema(description = "Desconto, caso haja", example = "0")
    private BigDecimal desconto;

    @Schema(description = "Valor total da conta de energia", example = "57.17")
    private BigDecimal valorTotal;

    @Schema(description = "Data da criação", example = "2022-06-04T17:13:47.218729")
    private LocalDateTime criado;

    @Schema(description = "Data da última atualização", example = "2022-06-04T17:13:47.218729")
    private LocalDateTime atualizado;

}
