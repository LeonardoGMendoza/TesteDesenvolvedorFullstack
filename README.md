# Cadastro de Pessoas - Prova Desenvolvedor Fullstack

API REST em Java com Spring Boot para cadastro de pessoas e consulta de nacionalidade provável pelo nome.

## Tecnologias

- Java 17
- Spring Boot 3.3 (Web, Data JPA, Validation, Security)
- Banco H2 em memória
- Front-end em HTML + JavaScript

## Como rodar

```
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`. A tela fica na raiz: `http://localhost:8080/`.

## Endpoints

O parâmetro escolhido para identificar a pessoa é o **documento** (11 números).

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| POST | `/registrarName` | Cadastra uma pessoa | Não |
| GET | `/list` | Lista as pessoas | Não |
| GET | `/list/{documento}` | Busca uma pessoa | Não |
| DELETE | `/list/{documento}` | Exclui uma pessoa | Sim |
| GET | `/findNacionalityByPerson/{documento}` | Nacionalidade provável pelo nome | Não |

Exemplo de cadastro:

```json
{
  "documento": "12345678901",
  "nome": "Leonardo",
  "sobrenome": "Gonzales",
  "email": "leonardo@email.com"
}
```

## Validações

- `documento`: obrigatório, exatamente 11 números (também validado nas rotas com parâmetro)
- `nome` e `sobrenome`: obrigatórios, máximo 60 caracteres
- `email`: obrigatório e em formato válido
- Documento duplicado retorna 409

## Autenticação

O DELETE foi considerado a operação mais crítica, por isso exige HTTP Basic.

Usuário padrão: `admin` / senha: `admin123` (pode ser alterado pelas variáveis `APP_USUARIO` e `APP_SENHA`).

```
curl -X DELETE -u admin:admin123 http://localhost:8080/list/12345678901
```

## Nacionalidade

A rota consulta `https://api.nationalize.io/?name={nome}`, pega o país com maior probabilidade e converte o código ISO para o nome do país usando `java.util.Locale`.
