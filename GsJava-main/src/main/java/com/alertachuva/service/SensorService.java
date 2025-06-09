package com.alertachuva.service;

import com.alertachuva.model.Sensor;
import com.alertachuva.model.Usuario;
import com.alertachuva.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Page<Sensor> listarTodos(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }

    public Optional<Sensor> buscarPorId(Long id) {
        return sensorRepository.findById(id);
    }

    public List<Sensor> listarPorUsuario(Usuario usuario) {
        return sensorRepository.findByUsuario(usuario);
    }

    public Sensor salvar(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public void deletar(Long id) {
        sensorRepository.deleteById(id);
    }

}
