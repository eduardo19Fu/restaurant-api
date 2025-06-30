package com.pangosoft.restaurant.model.llavesCompuestas;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BebidaCategoriaId implements Serializable {

    @Serial
    private static final long serialVersionUID = 5296607535799489039L;

    @Column(name = "id_bebida")
    private Integer bebidaId;

    @Column(name = "id_categoria")
    private Integer categoriaId;
}
