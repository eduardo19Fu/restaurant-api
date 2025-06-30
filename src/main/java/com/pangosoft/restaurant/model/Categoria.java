package com.pangosoft.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "categorias")
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 5353567613376071302L;

    @Id
    private Integer idCategoria;

    @NotBlank(message = "The category name should not be blank")
    private String nombre;

    private String descripcion;
}
