package net.guilhermejr.sistema.autenticacaoservice.service;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.autenticacaoservice.api.mapper.UsuarioMapper;
import net.guilhermejr.sistema.autenticacaoservice.api.request.UsuarioRequest;
import net.guilhermejr.sistema.autenticacaoservice.api.response.UsuarioResponse;
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
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;


    public UsuarioService(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public UsuarioResponse inserir(UsuarioRequest usuarioRequest) {

        if (!usuarioRequest.getSenha().equals(usuarioRequest.getConfirmarSenha())) {
            log.error("Usuário: {} - Senha e Confirmar senha devem ser iguais", usuarioRequest.getEmail());
            throw new ExceptionDefault("Senha e Confirmar senha devem ser iguais");
        }

        Usuario usuario = this.usuarioMapper.mapObject(usuarioRequest);

        // --- Perfil ---
        Set<Perfil> perfis = new HashSet<>();
        usuario.getPerfis().forEach(p -> {
            Perfil perfil = perfilRepository.findByDescricao(p.getDescricao())
                    .orElseThrow(() -> {
                        log.error("Usuário: {} - Perfil não encontrado", usuarioRequest.getEmail());
                        return new ExceptionNotFound("Perfil não encontrado");
                    });
            perfis.add(perfil);
        });
        usuario.setPerfis(perfis);

        // --- Criptografa senha ---
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioRequest.getSenha()));

        // --- Inclui as datas de criação e atualização ---
        usuario.setCriado(LocalDateTime.now(ZoneId.of("UTC")));
        usuario.setAtualizado(LocalDateTime.now(ZoneId.of("UTC")));

        // --- Salva usuário novo ---
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        log.info("Usuário: {} salvo com sucesso", usuarioSalvo.getEmail());

        return usuarioMapper.mapObject(usuarioSalvo);

    }

}
