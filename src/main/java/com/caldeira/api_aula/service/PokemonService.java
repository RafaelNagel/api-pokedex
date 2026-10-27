package com.caldeira.api_aula.service;

import com.caldeira.api_aula.dto.PokemonRequest;
import com.caldeira.api_aula.model.Pokemon;
import com.caldeira.api_aula.model.Treinador;
import com.caldeira.api_aula.repository.PokemonRepository;
import com.caldeira.api_aula.repository.TreinadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TreinadorRepository treinadorRepository;

    public PokemonService(
            PokemonRepository pokemonRepository,
            TreinadorRepository treinadorRepository) {
        this.pokemonRepository = pokemonRepository;
        this.treinadorRepository = treinadorRepository;
    }

    @Transactional(readOnly = true)
    public List<Pokemon> listarTodos() {
        return pokemonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pokemon buscarPorId(Long id) {
        return pokemonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pokemon nao encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Pokemon> listarPorTreinador(Long treinadorId) {
        if (!treinadorRepository.existsById(treinadorId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Treinador nao encontrado");
        }

        return pokemonRepository.findByTreinador_Id(treinadorId);
    }

    @Transactional
    public Pokemon criar(PokemonRequest request) {
        Treinador treinador = buscarTreinador(request.treinadorId());

        Pokemon pokemon = new Pokemon();
        pokemon.setNome(request.nome());
        pokemon.setTipo(request.tipo());
        pokemon.setTreinador(treinador);

        return pokemonRepository.save(pokemon);
    }

    @Transactional
    public Pokemon atualizar(Long id, PokemonRequest request) {
        Pokemon pokemon = buscarPorId(id);
        Treinador treinador = buscarTreinador(request.treinadorId());

        pokemon.setNome(request.nome());
        pokemon.setTipo(request.tipo());
        pokemon.setTreinador(treinador);

        return pokemonRepository.save(pokemon);
    }

    @Transactional
    public void deletar(Long id) {
        Pokemon pokemon = buscarPorId(id);
        pokemonRepository.delete(pokemon);
    }

    private Treinador buscarTreinador(Long treinadorId) {
        if (treinadorId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o treinadorId");
        }

        return treinadorRepository.findById(treinadorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Treinador nao encontrado"));
    }
}