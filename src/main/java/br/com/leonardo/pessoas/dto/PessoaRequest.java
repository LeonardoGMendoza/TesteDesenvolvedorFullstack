package br.com.leonardo.pessoas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PessoaRequest(

        @NotBlank(message = "documento é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "documento deve ter 11 números")
        String documento,

        @NotBlank(message = "nome é obrigatório")
        @Size(max = 60, message = "nome deve ter no máximo 60 caracteres")
        String nome,

        @NotBlank(message = "sobrenome é obrigatório")
        @Size(max = 60, message = "sobrenome deve ter no máximo 60 caracteres")
        String sobrenome,

        @NotBlank(message = "email é obrigatório")
        @Email(message = "email inválido")
        String email
) {
}
