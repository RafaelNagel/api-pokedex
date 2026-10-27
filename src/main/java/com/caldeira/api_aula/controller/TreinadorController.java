package com.caldeira.api_aula.controller;

import com.caldeira.api_aula.dto.TreinadorRequest;
import com.caldeira.api_aula.model.Treinador;
import com.caldeira.api_aula.service.TreinadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treinadores")
public class TreinadorController {

    private final TreinadorService treinadorService;

    public TreinadorController(TreinadorService treinadorService) {
        this.treinadorService = treinadorService;
    }

    @GetMapping
    public ResponseEntity<List<Treinador>> listarTodos() {
        return ResponseEntity.ok(treinadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treinador> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(treinadorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Treinador> criar(
            @RequestBody TreinadorRequest request) {
        Treinador treinador = treinadorService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(treinador);
    }
}