package br.com.leonardo.pessoas.repository;

import br.com.leonardo.pessoas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {
}
