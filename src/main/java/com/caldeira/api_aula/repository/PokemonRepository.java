package com.caldeira.api_aula.repository;

import com.caldeira.api_aula.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository
        extends JpaRepository<Pokemon, Long> {

    List<Pokemon> findByTreinador_Id(Long treinadorId);
}