package com.caldeira.api_aula.dto;

import java.util.List;

public record PokemonPokeApiDTO(
        int id,
        String nome,
        List<String> tipos,
        int altura,
        int peso,
        String imagem
) {
}
