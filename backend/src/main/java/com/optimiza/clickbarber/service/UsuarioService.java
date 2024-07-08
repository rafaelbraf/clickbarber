package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.usuario.Usuario;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioMapper;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioCadastrarDto;
import com.optimiza.clickbarber.repository.UsuarioRepository;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.USUARIO, Constants.Attribute.EMAIL, email));
    }

    public Usuario cadastrarUsuario(UsuarioCadastrarDto usuarioRegistrar) {
        var senhaCriptografada = bCryptPasswordEncoder.encode(usuarioRegistrar.getSenha());
        usuarioRegistrar.setSenha(senhaCriptografada);
        var usuario = usuarioMapper.toEntity(usuarioRegistrar);
        return usuarioRepository.save(usuario);
    }
}
