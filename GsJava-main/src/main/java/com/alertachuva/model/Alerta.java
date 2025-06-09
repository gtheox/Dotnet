package com.alertachuva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_leitura", nullable = false)
    private Leitura leitura;

    @NotNull(message = "Nível da água é obrigatório")
    @Column(name = "nivel_agua_cm")
    private Double nivelAguaCm;

    @Builder.Default
    @Column(name = "data_hora")
    private LocalDateTime dataHora = LocalDateTime.now();

    @NotBlank(message = "Status é obrigatório")
    private String status;
}
