package com.alertachuva.controller;

import com.alertachuva.model.Leitura;
import com.alertachuva.service.LeituraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/leituras")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    @GetMapping
    public ResponseEntity<Page<Leitura>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(leituraService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leitura> buscarPorId(@PathVariable Long id) {
        return leituraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Leitura> criar(@Valid @RequestBody Leitura leitura) {
        return ResponseEntity.ok(leituraService.salvar(leitura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leitura> atualizar(@PathVariable Long id, @Valid @RequestBody Leitura leituraAtualizada) {
        return leituraService.buscarPorId(id)
                .map(leitura -> {
                    leituraAtualizada.setId(leitura.getId());
                    return ResponseEntity.ok(leituraService.salvar(leituraAtualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (leituraService.buscarPorId(id).isPresent()) {
            leituraService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
