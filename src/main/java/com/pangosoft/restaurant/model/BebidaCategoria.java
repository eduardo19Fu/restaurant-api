package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.llavesCompuestas.BebidaCategoriaId;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "bebidas_categorias")
public class BebidaCategoria implements Serializable {

    @Serial
    private static final long serialVersionUID = -1296703411901239777L;

    @EmbeddedId
    private BebidaCategoriaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bebidaId")
    @JoinColumn(name = "id_bebida")
    @ToString.Exclude
    private Bebida bebida;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoriaId")
    @JoinColumn(name = "id_categoria")
    @ToString.Exclude
    private Categoria categoria;

    public BebidaCategoria(Bebida bebida, Categoria categoria) {
        this.bebida = bebida;
        this.categoria = categoria;
    }
}
