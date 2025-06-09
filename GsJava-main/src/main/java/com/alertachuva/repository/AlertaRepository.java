package com.alertachuva.repository;

import com.alertachuva.model.Alerta;
import com.alertachuva.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByLeitura(Leitura leitura);

    List<Alerta> findByStatus(String status); 

}
