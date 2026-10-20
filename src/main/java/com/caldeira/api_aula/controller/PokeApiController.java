package com.caldeira.api_aula.controller;

import com.caldeira.api_aula.client.PokeApiClient;
import com.caldeira.api_aula.dto.PokemonPokeApiDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/pokedex")
public class PokeApiController {

    private final PokeApiClient pokeApiClient;

    public PokeApiController(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    @GetMapping("/pokemon")
    public ResponseEntity<JsonNode> listarPrimeiros50() {
        return ResponseEntity.ok(
                pokeApiClient.listarPrimeiros50()
        );
    }

    @GetMapping("/pokemon/{nome}")
    public ResponseEntity<PokemonPokeApiDTO> buscarPorNome(
            @PathVariable String nome) {

        return ResponseEntity.ok(
                pokeApiClient.buscarDetalhado(nome)
        );
    }
}