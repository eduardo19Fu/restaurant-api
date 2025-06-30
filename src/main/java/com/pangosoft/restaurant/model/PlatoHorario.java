package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.llavesCompuestas.PlatoHorarioId;
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
@Table(name = "platos_horarios")
public class PlatoHorario implements Serializable {

    @Serial
    private static final long serialVersionUID = -3141163002322997126L;

    @EmbeddedId
    private PlatoHorarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("platoId")
    @JoinColumn(name = "id_plato")
    @ToString.Exclude
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("horarioId")
    @JoinColumn(name = "id_horario")
    @ToString.Exclude
    private Horario horario;
}
