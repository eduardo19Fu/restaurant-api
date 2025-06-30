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
public class BebidaHorarioId implements Serializable {

    @Serial
    private static final long serialVersionUID = 3839726200910462884L;

    @Column(name = "id_bebida")
    private Integer bebidaId;

    @Column(name = "id_horario")
    private Integer horarioId;
}
