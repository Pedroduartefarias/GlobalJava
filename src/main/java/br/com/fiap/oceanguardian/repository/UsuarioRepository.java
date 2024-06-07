package br.com.fiap.oceanguardian.repository;
import br.com.fiap.oceanguardian.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}