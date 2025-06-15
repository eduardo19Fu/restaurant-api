package com.pangosoft.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {

    @Serial
    private static final long serialVersionUID = 4651794256576475332L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idIngrediente;

    @NotNull(message = "The ingredient name cannot be null.")
    @NotBlank(message = "The ingredient name cannot be empty.")
    private String nombre;


    private BigDecimal stock;

    @ManyToOne
    @JoinColumn(name = "id_unidad_medida")
    private UnidadMedida unidadMedida;

    @PrePersist
    public void prepersist() {
        this.nombre = this.nombre.toUpperCase();
    }
}
