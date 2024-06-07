package br.com.fiap.oceanguardian.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.oceanguardian.model.Usuario;
import br.com.fiap.oceanguardian.repository.UsuarioRepository;
import br.com.fiap.oceanguardian.exception.ResourceNotFoundException;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario obterUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
