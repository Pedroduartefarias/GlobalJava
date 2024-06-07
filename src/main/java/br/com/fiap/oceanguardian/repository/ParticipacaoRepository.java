package br.com.fiap.oceanguardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oceanguardian.model.Participacao;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Long>{
    
}
