package com.caldeira.api_aula.service;

import com.caldeira.api_aula.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PokemonService {

    private final List<Pokemon> pokemons = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public List<Pokemon> listarTodos() {
        return new ArrayList<>(pokemons);
    }

    public Optional<Pokemon> buscarPorId(Long id) {
        return pokemons.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst();
    }

    public Pokemon criar(Pokemon pokemon) {
        pokemon.setId(contador.getAndIncrement());
        pokemons.add(pokemon);
        return pokemon;
    }

    public Optional<Pokemon> atualizar(Long id, Pokemon dadosAtualizados) {
        return buscarPorId(id).map(pokemon -> {
            pokemon.setNome(dadosAtualizados.getNome());
            pokemon.setTipo(dadosAtualizados.getTipo());
            return pokemon;
        });
    }

    public boolean deletar(Long id) {
        return pokemons.removeIf(pokemon -> pokemon.getId().equals(id));
    }
}

