package com.caldeira.api_aula.client;

import com.caldeira.api_aula.dto.PokemonPokeApiDTO;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class PokeApiClient {

    private static final String BASE_URL =
            "https://pokeapi.co/api/v2/pokemon";

    private final HttpClient httpClient;
    private final tools.jackson.databind.ObjectMapper objectMapper;

    public PokeApiClient(tools.jackson.databind.ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    public JsonNode listarPrimeiros50() {
        return consultar(BASE_URL + "?limit=50&offset=0");
    }

    public JsonNode buscarPorNome(String nome) {
        String nomeFormatado = URLEncoder.encode(
                nome.trim().toLowerCase(),
                StandardCharsets.UTF_8
        );

        return consultar(BASE_URL + "/" + nomeFormatado);
    }


    public PokemonPokeApiDTO buscarDetalhado(String nome) {
        JsonNode pokemon = buscarPorNome(nome);

        List<String> tipos = new ArrayList<>();

        for (JsonNode item : pokemon.path("types")) {
            tipos.add(item.path("type").path("name").asText());
        }

        JsonNode imagemNode = pokemon.path("sprites").path("front_default");

        String imagem = imagemNode.isNull() || imagemNode.isMissingNode()
                ? null
                : imagemNode.asText();

        return new PokemonPokeApiDTO(
                pokemon.path("id").asInt(),
                pokemon.path("name").asText(),
                tipos,
                pokemon.path("height").asInt(),
                pokemon.path("weight").asInt(),
                imagem
        );
    }


    private JsonNode consultar(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 404) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pokemon nao encontrado na PokeAPI"
                );
            }

            if (response.statusCode() < 200
                    || response.statusCode() >= 300) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "A PokeAPI retornou um erro"
                );
            }

            return objectMapper.readTree(response.body());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Consulta a PokeAPI interrompida"
            );

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Nao foi possivel consultar a PokeAPI"
            );
        }
    }
}
