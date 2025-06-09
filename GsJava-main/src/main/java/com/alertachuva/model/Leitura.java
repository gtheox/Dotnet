package com.alertachuva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leitura")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leitura")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sensor", nullable = false)
    private Sensor sensor;

    @NotNull(message = "Nível da água é obrigatório")
    @Column(name = "nivel_agua_cm")
    private Double nivelAguaCm;
    
    @Builder.Default
    @Column(name = "data_hora")
    private LocalDateTime dataHora = LocalDateTime.now();
}
