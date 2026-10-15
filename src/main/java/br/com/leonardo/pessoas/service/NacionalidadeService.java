package br.com.leonardo.pessoas.service;

import br.com.leonardo.pessoas.dto.NacionalidadeResponse;
import br.com.leonardo.pessoas.model.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class NacionalidadeService {

    private final PessoaService pessoaService;
    private final RestClient restClient;

    public NacionalidadeService(PessoaService pessoaService,
                                @Value("${nationalize.url}") String url) {
        this.pessoaService = pessoaService;
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    public NacionalidadeResponse buscarPorPessoa(String documento) {
        Pessoa pessoa = pessoaService.buscar(documento);

        NationalizeResult resultado = restClient.get()
                .uri(uri -> uri.queryParam("name", pessoa.getNome()).build())
                .retrieve()
                .body(NationalizeResult.class);

        if (resultado == null || resultado.country() == null || resultado.country().isEmpty()) {
            return new NacionalidadeResponse(pessoa.getNome(), null, "Não foi possível prever", 0);
        }

        // a API devolve uma lista de países, pego o de maior probabilidade
        Country maisProvavel = resultado.country().stream()
                .max(Comparator.comparingDouble(Country::probability))
                .get();

        // a API retorna o código ISO (ex: BR), o Locale converte para o nome do país
        String nomePais = new Locale("", maisProvavel.country_id())
                .getDisplayCountry(new Locale("pt", "BR"));

        return new NacionalidadeResponse(pessoa.getNome(), maisProvavel.country_id(),
                nomePais, maisProvavel.probability());
    }

    record NationalizeResult(String name, List<Country> country) {
    }

    record Country(String country_id, double probability) {
    }
}
