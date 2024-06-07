package br.com.fiap.oceanguardian.services;

import br.com.fiap.oceanguardian.model.Participacao;
import br.com.fiap.oceanguardian.repository.ParticipacaoRepository;
import br.com.fiap.oceanguardian.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoService {
    @Autowired
    private ParticipacaoRepository participacaoRepository;

    public Participacao registrarParticipacao(Participacao participacao) {
        return participacaoRepository.save(participacao);
    }

    public Page<Participacao> listarParticipacoes(Pageable pageable) {
        return participacaoRepository.findAll(pageable);
    }

    public Participacao obterParticipacao(Long id) {
        return participacaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Participação não encontrada"));
    }
}