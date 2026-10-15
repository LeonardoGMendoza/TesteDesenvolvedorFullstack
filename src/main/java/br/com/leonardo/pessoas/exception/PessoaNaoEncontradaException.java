package br.com.leonardo.pessoas.exception;

public class PessoaNaoEncontradaException extends RuntimeException {

    public PessoaNaoEncontradaException(String documento) {
        super("Pessoa não encontrada: " + documento);
    }
}
