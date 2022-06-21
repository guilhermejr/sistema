package net.guilhermejr.sistema.energia.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.exception.dto.ErrorDefaultDTO;
import net.guilhermejr.sistema.energia.exception.dto.ErrorRequestDTO;
import net.guilhermejr.sistema.energia.service.AcompanhamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Acompanhamentos")
@Log4j2
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('ENERGIA')")
@RequestMapping("/acompanhamentos")
public class AcompanhamentoController {

    private final AcompanhamentoService acompanhamentoService;

    public AcompanhamentoController(AcompanhamentoService acompanhamentoService) {
        this.acompanhamentoService = acompanhamentoService;
    }

    // --- Retornar -----------------------------------------------------------
    @Operation(summary = "Retorna acompanhamentos", responses = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AcompanhamentoResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<AcompanhamentoResponse>> retornar() {

        log.info("Retornando acompanhamentos");
        List<AcompanhamentoResponse> acompanhamentoResponseList = acompanhamentoService.retornar();
        return ResponseEntity.status(HttpStatus.OK).body(acompanhamentoResponseList);

    }

    // --- RetornarUm ---------------------------------------------------------
    @Operation(summary = "Retorna um acompanhamento", responses = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcompanhamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AcompanhamentoResponse> retornarUm(@PathVariable Long id) {

        log.info("Recuperando um acompanhamento: {}", id);
        AcompanhamentoResponse acompanhamentoResponse = acompanhamentoService.retornarUm(id);
        return ResponseEntity.status(HttpStatus.OK).body(acompanhamentoResponse);

    }

    // --- Inserir ------------------------------------------------------------
    @Operation(summary = "Insere um acompanhamento", responses = {
            @ApiResponse(responseCode = "201", description = "OK",content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcompanhamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRequestDTO.class)))
    })
    @PostMapping
    public ResponseEntity<AcompanhamentoResponse> inserir(@Valid @RequestBody AcompanhamentoRequest acompanhamentoRequest) {

        log.info("Inserindo acompanhamento");
        AcompanhamentoResponse acompanhamentoResponse = acompanhamentoService.inserir(acompanhamentoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(acompanhamentoResponse);

    }

    // --- Atualizar ----------------------------------------------------------
    @Operation(summary = "Atualiza um acompanhamento", responses = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcompanhamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AcompanhamentoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AcompanhamentoRequest acompanhamentoRequest) {

        log.info("Atualizando acompanhamento: {}", id);
        AcompanhamentoResponse acompanhamentoResponse = acompanhamentoService.atualizar(id, acompanhamentoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(acompanhamentoResponse);

    }

    // --- Apagar -------------------------------------------------------------
    @Operation(summary = "Apaga um acompanhamento", responses = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDefaultDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {

        log.info("Apagando acompanhamento: {}", id);
        acompanhamentoService.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
