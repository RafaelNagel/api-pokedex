package com.caldeira.api_aula.controller;

import com.caldeira.api_aula.model.Pokemon;
import com.caldeira.api_aula.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> buscarPorId(@PathVariable Long id) {
        return pokemonService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pokemon> criar (@RequestBody Pokemon pokemon) {
        Pokemon novoPokemon = pokemonService.criar(pokemon);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pokemon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pokemon> atualizar (
            @PathVariable Long id,
            @RequestBody Pokemon dadosAtualizados) {

     return pokemonService.atualizar(id, dadosAtualizados)
             .map(ResponseEntity::ok)
             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pokemon> deletar (@PathVariable Long id) {
        if (pokemonService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
