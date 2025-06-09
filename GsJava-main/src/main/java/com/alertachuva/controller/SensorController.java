package com.alertachuva.controller;

import com.alertachuva.model.Sensor;
import com.alertachuva.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping
    public ResponseEntity<Page<Sensor>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(sensorService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> buscarPorId(@PathVariable Long id) {
        return sensorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sensor> criar(@Valid @RequestBody Sensor sensor) {
        return ResponseEntity.ok(sensorService.salvar(sensor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> atualizar(@PathVariable Long id, @Valid @RequestBody Sensor sensorAtualizado) {
        return sensorService.buscarPorId(id)
                .map(sensor -> {
                    sensorAtualizado.setId(sensor.getId());
                    return ResponseEntity.ok(sensorService.salvar(sensorAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (sensorService.buscarPorId(id).isPresent()) {
            sensorService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
