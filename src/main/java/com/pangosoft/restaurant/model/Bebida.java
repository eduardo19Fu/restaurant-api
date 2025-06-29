package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "bebidas")
public class Bebida implements Serializable {

    @Serial
    private static final long serialVersionUID = -2357228098450179696L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBebida;

    @NotBlank(message = "The name of the drink must not be blank.")
    @Size(min = 2, message = "The name of the drink should be at least 5 characters long.")
    private String nombre;

    @Size(min = 20, message = "The description of the drink should be at least 20 characters long.")
    private String descripcion;

    private BigDecimal precio;

    private String imagen;

    @Enumerated(EnumType.STRING)
    private EstadoPlatoEnum estado;

    @OneToMany(
            mappedBy = "bebida",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BebidaCategoria> categorias;

    public void agregarCategoria(Categoria cat) {
        BebidaCategoria pivot = new BebidaCategoria(this, cat);
        this.categorias.add(pivot);
    }
}
