package br.com.fiap.oceanguardian.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.com.fiap.oceanguardian.model.Evento;
import br.com.fiap.oceanguardian.services.EventoService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Eventos", description = "Gerenciamento de eventos de limpeza")
@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @Operation(summary = "Listar todos os eventos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Evento>>> listarEventos(Pageable pageable) {
        Page<Evento> eventos = eventoService.listarEventos(pageable);
        List<EntityModel<Evento>> eventosModel = eventos.stream()
                .map(evento -> EntityModel.of(evento,
                        linkTo(methodOn(EventoController.class).listarEventos(pageable)).withSelfRel(),
                        linkTo(methodOn(EventoController.class).obterEvento(evento.getId())).withRel("evento")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(eventosModel, linkTo(methodOn(EventoController.class).listarEventos(pageable)).withSelfRel()));
    }

    @Operation(summary = "Obter um evento pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Evento>> obterEvento(@PathVariable Long id) {
        Evento evento = eventoService.obterEvento(id);
        return ResponseEntity.ok(EntityModel.of(evento, 
            linkTo(methodOn(EventoController.class).obterEvento(id)).withSelfRel(),
            linkTo(methodOn(EventoController.class).listarEventos(Pageable.unpaged())).withRel("eventos")));
    }

    @Operation(summary = "Criar um novo evento")
    @PostMapping
    public ResponseEntity<EntityModel<Evento>> criarEvento(@jakarta.validation.Valid @RequestBody Evento evento) {
        Evento novoEvento = eventoService.criarEvento(evento);
        return ResponseEntity.created(linkTo(methodOn(EventoController.class).obterEvento(novoEvento.getId())).toUri())
                .body(EntityModel.of(novoEvento,
                        linkTo(methodOn(EventoController.class).obterEvento(novoEvento.getId())).withSelfRel()));
    }
}