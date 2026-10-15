package br.com.leonardo.pessoas.service;

import br.com.leonardo.pessoas.dto.PessoaRequest;
import br.com.leonardo.pessoas.exception.PessoaJaCadastradaException;
import br.com.leonardo.pessoas.exception.PessoaNaoEncontradaException;
import br.com.leonardo.pessoas.model.Pessoa;
import br.com.leonardo.pessoas.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa registrar(PessoaRequest request) {
        if (repository.existsById(request.documento())) {
            throw new PessoaJaCadastradaException(request.documento());
        }
        Pessoa pessoa = new Pessoa(
                request.documento(),
                request.nome().trim(),
                request.sobrenome().trim(),
                request.email().trim().toLowerCase());
        return repository.save(pessoa);
    }

    public List<Pessoa> listar() {
        return repository.findAll();
    }

    public Pessoa buscar(String documento) {
        return repository.findById(documento)
                .orElseThrow(() -> new PessoaNaoEncontradaException(documento));
    }

    public void excluir(String documento) {
        Pessoa pessoa = buscar(documento);
        repository.delete(pessoa);
    }
}
