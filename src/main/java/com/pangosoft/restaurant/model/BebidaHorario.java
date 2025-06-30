package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.llavesCompuestas.BebidaHorarioId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "bebidas_horarios")
public class BebidaHorario implements Serializable {

    @Serial
    private static final long serialVersionUID = -2385778596745533198L;

    @EmbeddedId
    private BebidaHorarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bebidaId")
    @JoinColumn(name = "id_bebida")
    @ToString.Exclude
    private Bebida bebida;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("horarioId")
    @JoinColumn(name = "id_horario")
    @ToString.Exclude
    private Horario horario;

    public BebidaHorario(Bebida bebida, Horario horario) {
        this.bebida = bebida;
        this.horario = horario;
    }

}
