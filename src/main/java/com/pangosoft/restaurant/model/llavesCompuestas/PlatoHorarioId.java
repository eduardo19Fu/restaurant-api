package com.pangosoft.restaurant.model.llavesCompuestas;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PlatoHorarioId implements Serializable {

    @Serial
    private static final long serialVersionUID = -7671212371910484780L;

    @Column(name = "id_plato")
    private Integer platoId;

    @Column(name = "id_horario")
    private Integer horarioId;
}
