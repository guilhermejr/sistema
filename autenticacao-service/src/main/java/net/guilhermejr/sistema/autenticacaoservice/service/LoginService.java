package net.guilhermejr.sistema.autenticacaoservice.service;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.autenticacaoservice.api.request.LoginRequest;
import net.guilhermejr.sistema.autenticacaoservice.api.response.JWTResponde;
import net.guilhermejr.sistema.autenticacaoservice.config.security.JwtProvider;
import net.guilhermejr.sistema.autenticacaoservice.config.security.UserDetailsImpl;
import net.guilhermejr.sistema.autenticacaoservice.domain.entity.Usuario;
import net.guilhermejr.sistema.autenticacaoservice.domain.repository.UsuarioRepository;
import net.guilhermejr.sistema.autenticacaoservice.exception.ExceptionDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@Service
public class LoginService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository usuarioRepository;

    public LoginService(JwtProvider jwtProvider, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public JWTResponde login (LoginRequest loginRequest) {

        try {

            // --- Realiza autenticação e grava no contexto ---
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // --- Gera token jwt ---
            String jwt = jwtProvider.generateJwt(authentication);
            log.info("Usuário: {} autenticado com sucesso", loginRequest.getEmail());

            // --- Atualiza último acesso ---
            UserDetailsImpl usuarioLogado = (UserDetailsImpl) authentication.getPrincipal();
            Usuario usuarioDB = usuarioRepository.findById(usuarioLogado.getId()).get();
            usuarioDB.setUltimoAcesso(LocalDateTime.now(ZoneId.of("UTC")));
            usuarioRepository.save(usuarioDB);

            // --- Retorno ---
            return new JWTResponde(jwt);

        } catch (Exception e) {

            log.error("Usuário: {} não informou uma chave email:senha válidos, ou está inativo.", loginRequest.getEmail());
            throw new ExceptionDefault("Combinação de e-mail e senha inválidos.");

        }

    }
}
