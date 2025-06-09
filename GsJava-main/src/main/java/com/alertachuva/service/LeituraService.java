package com.alertachuva.service;

import com.alertachuva.model.Leitura;
import com.alertachuva.model.Sensor;
import com.alertachuva.repository.LeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class LeituraService {

    @Autowired
    private LeituraRepository leituraRepository;

    public Page<Leitura> listarTodos(Pageable pageable) {
        return leituraRepository.findAll(pageable);
    }

    public Optional<Leitura> buscarPorId(Long id) {
        return leituraRepository.findById(id);
    }

    public List<Leitura> listarPorSensor(Sensor sensor) {
        return leituraRepository.findBySensor(sensor);
    }

    public Leitura salvar(Leitura leitura) {
        return leituraRepository.save(leitura);
    }

    public void deletar(Long id) {
        leituraRepository.deleteById(id);
    }

}
