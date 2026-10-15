package br.com.leonardo.pessoas.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // erros de validação do body (POST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> body(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        return erro(HttpStatus.BAD_REQUEST, erros);
    }

    // erros de validação do path (GET/DELETE)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> path(ConstraintViolationException ex) {
        List<String> erros = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return erro(HttpStatus.BAD_REQUEST, erros);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> metodo(HandlerMethodValidationException ex) {
        List<String> erros = ex.getAllErrors().stream()
                .map(e -> e.getDefaultMessage())
                .toList();
        return erro(HttpStatus.BAD_REQUEST, erros);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrada(PessoaNaoEncontradaException ex) {
        return erro(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(PessoaJaCadastradaException.class)
    public ResponseEntity<Map<String, Object>> duplicada(PessoaJaCadastradaException ex) {
        return erro(HttpStatus.CONFLICT, List.of(ex.getMessage()));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, Object>> apiExterna(RestClientException ex) {
        return erro(HttpStatus.BAD_GATEWAY, List.of("Erro ao consultar a API de nacionalidade"));
    }

    private ResponseEntity<Map<String, Object>> erro(HttpStatus status, List<String> erros) {
        return ResponseEntity.status(status).body(Map.of("status", status.value(), "erros", erros));
    }
}
