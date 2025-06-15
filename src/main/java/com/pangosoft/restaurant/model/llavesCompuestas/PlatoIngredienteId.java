package com.pangosoft.restaurant.model.llavesCompuestas;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Embeddable
public class PlatoIngredienteId implements Serializable {

    @Serial
    private static final long serialVersionUID = -1112642820387911872L;

    @Column(name = "id_plato")
    private Integer platoId;

    @Column(name = "id_ingrediente")
    private Integer ingredienteId;
}
