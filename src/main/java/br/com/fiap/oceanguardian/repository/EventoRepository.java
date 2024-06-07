package br.com.fiap.oceanguardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oceanguardian.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{
    
}
