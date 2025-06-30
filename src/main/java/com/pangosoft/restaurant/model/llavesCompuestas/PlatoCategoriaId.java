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
public class PlatoCategoriaId implements Serializable {

    @Serial
    private static final long serialVersionUID = 4212836707999007586L;

    @Column(name = "id_plato")
    private Integer platoId;

    @Column(name = "id_categoria")
    private Integer categoriaId;
}
