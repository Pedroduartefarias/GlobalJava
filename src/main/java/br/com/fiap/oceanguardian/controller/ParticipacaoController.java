package br.com.fiap.oceanguardian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.oceanguardian.model.Participacao;
import br.com.fiap.oceanguardian.services.ParticipacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Participações", description = "Gerenciamento de participações em eventos de limpeza")
@RestController
@RequestMapping("/api/participacoes")
public class ParticipacaoController {
    @Autowired
    private ParticipacaoService participacaoService;

    @Operation(summary = "Listar todas as participações")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Participacao>>> listarParticipacoes(Pageable pageable) {
        Page<Participacao> participacoes = participacaoService.listarParticipacoes(pageable);
        List<EntityModel<Participacao>> participacoesModel = participacoes.stream()
                .map(participacao -> EntityModel.of(participacao,
                        linkTo(methodOn(ParticipacaoController.class).listarParticipacoes(pageable)).withSelfRel(),
                        linkTo(methodOn(ParticipacaoController.class).obterParticipacao(participacao.getId())).withRel("participacao")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(participacoesModel, linkTo(methodOn(ParticipacaoController.class).listarParticipacoes(pageable)).withSelfRel()));
    }

    @Operation(summary = "Obter uma participação pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Participacao>> obterParticipacao(@PathVariable Long id) {
        Participacao participacao = participacaoService.obterParticipacao(id);
        return ResponseEntity.ok(EntityModel.of(participacao, 
            linkTo(methodOn(ParticipacaoController.class).obterParticipacao(id)).withSelfRel(),
            linkTo(methodOn(ParticipacaoController.class).listarParticipacoes(Pageable.unpaged())).withRel("participacoes")));
    }

    @Operation(summary = "Criar uma nova participação")
    @PostMapping
    public ResponseEntity<EntityModel<Participacao>> criarParticipacao(@jakarta.validation.Valid @RequestBody Participacao participacao) {
        Participacao novaParticipacao = participacaoService.registrarParticipacao(participacao);
        return ResponseEntity.created(linkTo(methodOn(ParticipacaoController.class).obterParticipacao(novaParticipacao.getId())).toUri())
                .body(EntityModel.of(novaParticipacao,
                        linkTo(methodOn(ParticipacaoController.class).obterParticipacao(novaParticipacao.getId())).withSelfRel()));
    }
}