package com.caldeira.api_aula.controller;

import com.caldeira.api_aula.dto.PokemonRequest;
import com.caldeira.api_aula.model.Pokemon;
import com.caldeira.api_aula.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<List<Pokemon>> listarTodos() {
        return ResponseEntity.ok(pokemonService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(pokemonService.buscarPorId(id));
    }

    @GetMapping("/treinador/{treinadorId}")
    public ResponseEntity<List<Pokemon>> listarPorTreinador(
            @PathVariable Long treinadorId) {
        return ResponseEntity.ok(
                pokemonService.listarPorTreinador(treinadorId));
    }

    @PostMapping
    public ResponseEntity<Pokemon> criar(
            @RequestBody PokemonRequest request) {
        Pokemon novoPokemon = pokemonService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoPokemon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pokemon> atualizar(
            @PathVariable Long id,
            @RequestBody PokemonRequest request) {
        return ResponseEntity.ok(
                pokemonService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        pokemonService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}