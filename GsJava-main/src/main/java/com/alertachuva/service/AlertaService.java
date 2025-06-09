package com.alertachuva.service;

import com.alertachuva.model.Alerta;
import com.alertachuva.model.Leitura;
import com.alertachuva.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    public Page<Alerta> listarTodos(Pageable pageable) {
        return alertaRepository.findAll(pageable);
    }

    public Optional<Alerta> buscarPorId(Long id) {
        return alertaRepository.findById(id);
    }

    public List<Alerta> listarPorLeitura(Leitura leitura) {
        return alertaRepository.findByLeitura(leitura);
    }

    public List<Alerta> listarPorStatus(String status) {
        return alertaRepository.findByStatus(status);
    }

    public Alerta salvar(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public void deletar(Long id) {
        alertaRepository.deleteById(id);
    }

}
