package br.com.leonardo.pessoas.controller;

import br.com.leonardo.pessoas.dto.NacionalidadeResponse;
import br.com.leonardo.pessoas.dto.PessoaRequest;
import br.com.leonardo.pessoas.model.Pessoa;
import br.com.leonardo.pessoas.service.NacionalidadeService;
import br.com.leonardo.pessoas.service.PessoaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PessoaController {

    private static final String DOC_REGEX = "\\d{11}";
    private static final String DOC_MSG = "documento deve ter 11 números";

    private final PessoaService pessoaService;
    private final NacionalidadeService nacionalidadeService;

    public PessoaController(PessoaService pessoaService, NacionalidadeService nacionalidadeService) {
        this.pessoaService = pessoaService;
        this.nacionalidadeService = nacionalidadeService;
    }

    @PostMapping("/registrarName")
    public ResponseEntity<Pessoa> registrar(@RequestBody @Valid PessoaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.registrar(request));
    }

    @GetMapping("/list")
    public List<Pessoa> listar() {
        return pessoaService.listar();
    }

    @GetMapping("/list/{documento}")
    public Pessoa buscar(@PathVariable @Pattern(regexp = DOC_REGEX, message = DOC_MSG) String documento) {
        return pessoaService.buscar(documento);
    }

    @DeleteMapping("/list/{documento}")
    public ResponseEntity<Void> excluir(@PathVariable @Pattern(regexp = DOC_REGEX, message = DOC_MSG) String documento) {
        pessoaService.excluir(documento);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findNacionalityByPerson/{documento}")
    public NacionalidadeResponse nacionalidade(@PathVariable @Pattern(regexp = DOC_REGEX, message = DOC_MSG) String documento) {
        return nacionalidadeService.buscarPorPessoa(documento);
    }
}
