package com.alertachuva.repository;

import com.alertachuva.model.Sensor;
import com.alertachuva.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findByUsuario(Usuario usuario); 

}
