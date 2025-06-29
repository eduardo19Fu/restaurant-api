package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.llavesCompuestas.PlatoCategoriaId;

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
@Entity
@Table(name = "platos_categorias")
public class PlatoCategoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 8781444322888837355L;

    @EmbeddedId
    private PlatoCategoriaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("platoId")
    @JoinColumn(name = "id_plato")
    @ToString.Exclude
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoriaId")
    @JoinColumn(name = "id_categoria")
    @ToString.Exclude
    private Categoria categoria;
}
