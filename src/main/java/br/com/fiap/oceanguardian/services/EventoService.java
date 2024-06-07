package br.com.fiap.oceanguardian.services;

import br.com.fiap.oceanguardian.repository.EventoRepository;
import br.com.fiap.oceanguardian.model.Evento;
import br.com.fiap.oceanguardian.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public Evento criarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Page<Evento> listarEventos(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }

    public Evento obterEvento(Long id) {
        return eventoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
    }
}