package com.caldeira.api_aula.dto;

public record PokemonRequest(
        String nome,
        String tipo,
        Long treinadorId
) {
}