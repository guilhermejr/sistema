package net.guilhermejr.sistema.energia.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import net.guilhermejr.sistema.energia.api.request.validation.constraints.DataBrasil;
import net.guilhermejr.sistema.energia.api.request.validation.constraints.ValorMonetario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Request para acompanhamento")
public class AcompanhamentoRequest {

    @NotBlank
    @DataBrasil
    @Schema(description = "Data do início do ciclo de consumo", example = "18/11/2021")
    private String inicio;

    @NotBlank
    @DataBrasil
    @Schema(description = "Data do fim do ciclo de consumo", example = "20/12/2021")
    private String fim;

    @NotBlank
    @ValorMonetario
    @Schema(description = "Energia gerada", example = "653,50")
    private String energiaGerada;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Energia injetada", example = "485")
    private Integer energiaInjetada;

    @NotNull
    @Positive
    @Schema(description = "Energia consumida", example = "339")
    private Integer energiaConsumida;

    @NotBlank
    @ValorMonetario
    @Schema(description = "TUSD", example = "29,04")
    private String tusd;

    @NotBlank
    @ValorMonetario
    @Schema(description = "TE", example = "16,56")
    private String te;

    @NotBlank
    @ValorMonetario
    @Schema(description = "Bandeira tarifária", example = "10,45")
    private String bandeira;

    @NotBlank
    @ValorMonetario
    @Schema(description = "Iluminação pública", example = "1,12")
    private String iluminacaoPublica;

    @NotBlank
    @ValorMonetario
    @Schema(description = "Desconto, caso haja", example = "0,00")
    private String desconto;

}
