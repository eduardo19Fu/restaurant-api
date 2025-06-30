package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;

import com.pangosoft.restaurant.model.llavesCompuestas.PlatoHorarioId;
import com.pangosoft.restaurant.model.llavesCompuestas.PlatoIngredienteId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "platos")
public class Plato implements Serializable {

    @Serial
    private static final long serialVersionUID = -6730684034524855906L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlato;

    @NotNull(message = "The name of the dish cannot be null.")
    @NotBlank(message = "The name of the dish cannot be blank, it must contain at least 5 characters")
    @Size(min = 5)
    private String nombre;

    @NotBlank(message = "The description cannot be blank.")
    private String descripcion;

    @NotNull(message = "The price cannot be null.")
    @Digits(integer = 10, fraction = 2, message = "The value is not valid.  The price has to be formed by integer {integer} and fraction {fraction}")
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    private EstadoPlatoEnum estado;

    @OneToMany(
            mappedBy = "plato",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PlatoIngrediente> ingredientes = new HashSet<>();

    @OneToMany(
            mappedBy = "plato",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PlatoCategoria> categorias = new HashSet<>();

    @OneToMany(
            mappedBy = "plato",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PlatoHorario> horarios = new HashSet<>();

    /**
     * Agregar un ingrediente y cantidad a este plato
     * */
    public void agregarIngrediente(Ingrediente ingrediente, BigDecimal cantidad, UnidadMedida unidadMedida) {
        PlatoIngrediente pivot = PlatoIngrediente.builder()
                .id(new PlatoIngredienteId(this.idPlato, ingrediente.getIdIngrediente()))
                .plato(this)
                .ingrediente(ingrediente)
                .cantidad(cantidad)
                .unidadMedida(unidadMedida)
                .build();
        ingredientes.add(pivot);
    }

    public void removerIngrediente(Ingrediente ing) {
        ingredientes.removeIf(pi -> pi.getIngrediente().equals(ing));
    }

    /**
     * Agregar una categoria nueva al plato
     * */

    public void agregarCategoria(Categoria categoria) {
        PlatoCategoria pivot = new PlatoCategoria(this, categoria);
        this.categorias.add(pivot);
    }

    /**
     * Desasocia una categoria
     * */
    public void removerCategoria(Categoria categoria) {
        categorias.removeIf(cat -> cat.getCategoria().equals(categoria));
    }

    /**
     * Asocia un turno (horario) a este plato.
     */
    public void agregarHorario(Horario horario) {
        PlatoHorario pivot = new PlatoHorario(
                new PlatoHorarioId(this.getIdPlato(), horario.getIdHorario()),
                this,
                horario
        );
        this.horarios.add(pivot);
    }

    /**
     * Desasocia un turno.
     */
    public void removerHorario(Horario horario) {
        this.horarios.removeIf(ph -> ph.getHorario().equals(horario));
    }

}
