package com.caldeira.api_aula.service;

import com.caldeira.api_aula.dto.TreinadorRequest;
import com.caldeira.api_aula.model.Treinador;
import com.caldeira.api_aula.repository.TreinadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TreinadorService {

    private final TreinadorRepository treinadorRepository;

    public TreinadorService(TreinadorRepository treinadorRepository) {
        this.treinadorRepository = treinadorRepository;
    }

    @Transactional(readOnly = true)
    public List<Treinador> listarTodos() {
        return treinadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Treinador buscarPorId(Long id) {
        return treinadorRepository.findById(id)
                .orElseThrow(() ->
                        new org.springframework.web.server
                                .ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "Treinador nao encontrado"));
    }

    @Transactional
    public Treinador criar(TreinadorRequest request) {
        Treinador treinador = new Treinador();
        treinador.setNome(request.nome());

        return treinadorRepository.save(treinador);
    }
}