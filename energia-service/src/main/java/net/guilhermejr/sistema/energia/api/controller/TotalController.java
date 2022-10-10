package net.guilhermejr.sistema.energia.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.energia.api.response.TotalResponse;
import net.guilhermejr.sistema.energia.exception.dto.ErrorDefaultDTO;
import net.guilhermejr.sistema.energia.service.TotalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Total")
@Log4j2
@AllArgsConstructor
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping("/total")
public class TotalController {

    private final TotalService totalService;

    // --- Retornar -----------------------------------------------------------
    @Operation(summary = "Retorna totais", responses = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(mediaType = "application/json", schema = @Schema(implementation = TotalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class)))
    })
    @GetMapping
    public ResponseEntity<TotalResponse> retornar() {

        log.info("Retornando total");
        TotalResponse totalResponse = totalService.retornar();
        return ResponseEntity.status(HttpStatus.OK).body(totalResponse);

    }

}
