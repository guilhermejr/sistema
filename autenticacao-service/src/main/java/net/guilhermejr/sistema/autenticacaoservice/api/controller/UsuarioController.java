package net.guilhermejr.sistema.autenticacaoservice.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.autenticacaoservice.api.request.UsuarioRequest;
import net.guilhermejr.sistema.autenticacaoservice.api.response.UsuarioResponse;
import net.guilhermejr.sistema.autenticacaoservice.config.security.AuthenticationCurrentUserService;
import net.guilhermejr.sistema.autenticacaoservice.config.security.UserDetailsImpl;
import net.guilhermejr.sistema.autenticacaoservice.exception.dto.ErrorRequestDTO;
import net.guilhermejr.sistema.autenticacaoservice.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public UsuarioController(UsuarioService usuarioService, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.usuarioService = usuarioService;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }

    // --- Inserir ------------------------------------------------------------
    @Operation(summary = "Insere um usuário", responses = {
            @ApiResponse(responseCode = "201", description = "OK",content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRequestDTO.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> inserir(@Valid @RequestBody UsuarioRequest usuarioRequest) {

        UserDetailsImpl usuario = authenticationCurrentUserService.getCurrentUser();

        log.info("Inserindo usuário: {}", usuarioRequest.getEmail());
        UsuarioResponse usuarioResponse = usuarioService.inserir(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);

    }

}
