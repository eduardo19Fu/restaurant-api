package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.llavesCompuestas.PlatoIngredienteId;
import jakarta.persistence.Column;
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
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "plato_ingredientes")
public class PlatoIngrediente implements Serializable {

    @Serial
    private static final long serialVersionUID = 156904371074410323L;

    @EmbeddedId
    private PlatoIngredienteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("platoId")
    @JoinColumn(name = "id_plato")
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredienteId")
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(nullable = false)
    private BigDecimal cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_medida", nullable = false)
    private UnidadMedida unidadMedida;

    /**
     * Constructor de conveniencia para crear la relación
     */
    public PlatoIngrediente(Plato plato, Ingrediente ingrediente, BigDecimal cantidad, UnidadMedida unidadMedida) {
        this.plato = plato;
        this.ingrediente = ingrediente;
        this.id = new PlatoIngredienteId(plato.getIdPlato(), ingrediente.getIdIngrediente());
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
    }

}
