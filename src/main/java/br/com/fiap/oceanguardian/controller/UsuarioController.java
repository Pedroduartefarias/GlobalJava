package br.com.fiap.oceanguardian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.oceanguardian.model.Usuario;
import br.com.fiap.oceanguardian.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Usuários", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
     @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos os usuários")
    @GetMapping("/listarusuarios")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listarUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.listarUsuarios(pageable);
        List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).obterUsuario(usuario.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).listarUsuarios(pageable)).withRel("usuarios")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(usuariosModel, linkTo(methodOn(UsuarioController.class).listarUsuarios(pageable)).withSelfRel()));
    }

    @Operation(summary = "Obter um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> obterUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterUsuario(id);
        return ResponseEntity.ok(EntityModel.of(usuario, 
            linkTo(methodOn(UsuarioController.class).obterUsuario(id)).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).listarUsuarios(Pageable.unpaged())).withRel("usuarios")));
    }

    @Operation(summary = "Criar um novo usuário")
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> salvarUsuario(@jakarta.validation.Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.created(linkTo(methodOn(UsuarioController.class).obterUsuario(novoUsuario.getId())).toUri())
                .body(EntityModel.of(novoUsuario,
                        linkTo(methodOn(UsuarioController.class).obterUsuario(novoUsuario.getId())).withSelfRel()));
    }
}