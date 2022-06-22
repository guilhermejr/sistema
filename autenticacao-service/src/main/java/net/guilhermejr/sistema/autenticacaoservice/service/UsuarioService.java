package net.guilhermejr.sistema.autenticacaoservice.service;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.autenticacaoservice.api.mapper.UsuarioMapper;
import net.guilhermejr.sistema.autenticacaoservice.api.request.UsuarioRequest;
import net.guilhermejr.sistema.autenticacaoservice.api.response.UsuarioResponse;
import net.guilhermejr.sistema.autenticacaoservice.config.security.AuthenticationCurrentUserService;
import net.guilhermejr.sistema.autenticacaoservice.domain.entity.Perfil;
import net.guilhermejr.sistema.autenticacaoservice.domain.entity.Usuario;
import net.guilhermejr.sistema.autenticacaoservice.domain.repository.PerfilRepository;
import net.guilhermejr.sistema.autenticacaoservice.domain.repository.UsuarioRepository;
import net.guilhermejr.sistema.autenticacaoservice.exception.ExceptionDefault;
import net.guilhermejr.sistema.autenticacaoservice.exception.ExceptionNotFound;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;


    public UsuarioService(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }

    @Transactional
    public UsuarioResponse inserir(UsuarioRequest usuarioRequest) {

        if (!usuarioRequest.getSenha().equals(usuarioRequest.getConfirmarSenha())) {
            log.error("Usuário: {} - Senha e Confirmar senha devem ser iguais", usuarioRequest.getEmail());
            throw new ExceptionDefault("Senha e Confirmar senha devem ser iguais");
        }

        System.out.println("---> REQUEST");
        System.out.println(usuarioRequest);

        Usuario usuarioLogado = usuarioMapper.mapUserDetailsImpl(authenticationCurrentUserService.getCurrentUser());
        Usuario usuario = this.usuarioMapper.mapObject(usuarioRequest);

        System.out.println("---> ANTES");
        System.out.println(usuario);

        // --- Perfil ---
        List<Perfil> perfis = new ArrayList<>();
        usuario.getPerfis().forEach(p -> {
            Perfil perfil = perfilRepository.findByDescricao(p.getDescricao())
                    .orElseThrow(() -> {
                        log.error("Usuário: {} - Perfil não encontrado", usuarioRequest.getEmail());
                        return new ExceptionNotFound("Perfil não encontrado");
                    });
            perfis.add(perfil);
        });
        usuario.setPerfis(perfis);
        usuario.setUsuario(usuarioLogado);

        // --- Criptografa senha ---
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioRequest.getSenha()));

        // --- Inclui as datas de criação e atualização ---
        usuario.setCriado(LocalDateTime.now(ZoneId.of("UTC")));
        usuario.setAtualizado(LocalDateTime.now(ZoneId.of("UTC")));

        System.out.println("---> DEPOIS");
        System.out.println(usuario);

        // --- Salva usuário novo ---
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        log.info("Usuário: {} salvo com sucesso", usuarioSalvo.getEmail());

        return usuarioMapper.mapObject(usuarioSalvo);

    }

}
