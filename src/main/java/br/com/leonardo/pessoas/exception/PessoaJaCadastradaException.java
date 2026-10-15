package br.com.leonardo.pessoas.exception;

public class PessoaJaCadastradaException extends RuntimeException {

    public PessoaJaCadastradaException(String documento) {
        super("Já existe uma pessoa com o documento " + documento);
    }
}
