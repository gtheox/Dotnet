package com.alertachuva.controller;

import com.alertachuva.model.Alerta;
import com.alertachuva.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    public ResponseEntity<Page<Alerta>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(alertaService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id) {
        return alertaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Alerta>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(alertaService.listarPorStatus(status));
    }

    @PostMapping
    public ResponseEntity<Alerta> criar(@Valid @RequestBody Alerta alerta) {
        return ResponseEntity.ok(alertaService.salvar(alerta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> atualizar(@PathVariable Long id, @Valid @RequestBody Alerta alertaAtualizado) {
        return alertaService.buscarPorId(id)
                .map(alerta -> {
                    alertaAtualizado.setId(alerta.getId());
                    return ResponseEntity.ok(alertaService.salvar(alertaAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (alertaService.buscarPorId(id).isPresent()) {
            alertaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
