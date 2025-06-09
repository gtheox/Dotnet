package com.alertachuva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "sensor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id;

    @NotBlank(message = "Localização é obrigatória")
    @Size(max = 150)
    private String localizacao;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "Nível de alerta mínimo é obrigatório")
    @Column(name = "nivel_alerta_minimo_cm")
    private Double nivelAlertaMinimoCm;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}

