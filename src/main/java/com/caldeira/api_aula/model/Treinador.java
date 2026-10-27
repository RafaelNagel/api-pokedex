package com.caldeira.api_aula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treinadores")
@Getter
@Setter
@NoArgsConstructor
public class Treinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "treinador")
    private List<Pokemon> pokemons = new ArrayList<>();
}