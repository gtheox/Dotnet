package com.alertachuva.repository;

import com.alertachuva.model.Leitura;
import com.alertachuva.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    List<Leitura> findBySensor(Sensor sensor);

    List<Leitura> findBySensorId(Long sensorId); 

}
