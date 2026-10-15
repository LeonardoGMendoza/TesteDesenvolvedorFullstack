package br.com.leonardo.pessoas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pessoa {

    @Id
    @Column(length = 11)
    private String documento;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    private String email;

    public Pessoa() {
    }

    public Pessoa(String documento, String nome, String sobrenome, String email) {
        this.documento = documento;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }
}
