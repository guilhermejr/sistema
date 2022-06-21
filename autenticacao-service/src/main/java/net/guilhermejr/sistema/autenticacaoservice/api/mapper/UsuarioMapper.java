package net.guilhermejr.sistema.autenticacaoservice.api.mapper;

import net.guilhermejr.sistema.autenticacaoservice.api.request.UsuarioRequest;
import net.guilhermejr.sistema.autenticacaoservice.api.response.UsuarioResponse;
import net.guilhermejr.sistema.autenticacaoservice.config.ModelMapperConfig;
import net.guilhermejr.sistema.autenticacaoservice.config.security.UserDetailsImpl;
import net.guilhermejr.sistema.autenticacaoservice.domain.entity.Perfil;
import net.guilhermejr.sistema.autenticacaoservice.domain.entity.Usuario;
import org.modelmapper.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper extends ModelMapperConfig {

    public UsuarioMapper() {

        Converter<Set<String>, Set<Perfil>> perfisConverter = ctx -> {

            Set<Perfil> perfis = new HashSet<>();
            ctx.getSource().forEach(a -> {
                perfis.add(Perfil.builder().descricao(a).build());
            });
            return perfis;
        };

        Converter<Set<Perfil>, List<GrantedAuthority>> perfisImplConverter = ctx -> ctx.getSource().stream().map(p -> new SimpleGrantedAuthority(p.getDescricao())).collect(Collectors.toList());

        this.modelMapper.createTypeMap(UsuarioRequest.class, Usuario.class)
                .addMappings(mapper -> mapper.using(perfisConverter).map(UsuarioRequest::getPerfis, Usuario::setPerfis));

        this.modelMapper.createTypeMap(Usuario.class, UserDetailsImpl.class)
                .addMappings(mapper -> mapper.using(perfisImplConverter).map(Usuario::getPerfis, UserDetailsImpl::setPerfis));
    }

    public UserDetailsImpl mapUserDetailsImpl(Usuario usuario) {
        return this.mapObject(usuario, UserDetailsImpl.class);
    }

    public Usuario mapObject(UsuarioRequest usuarioRequest) {
        return this.mapObject(usuarioRequest, Usuario.class);
    }

    public UsuarioResponse mapObject(Usuario usuario) {
        return this.mapObject(usuario, UsuarioResponse.class);
    }

    public List<UsuarioResponse> mapList(List<Usuario> usuarios) {
        return this.mapList(usuarios, UsuarioResponse.class);
    }

}
