package com.pangosoft.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "cargos")
public class Cargo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2357789564454633193L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCargo;

    @NotNull(message = "The position cannot be null.")
    @NotBlank(message = "The position cannot be blank.")
    private String cargo;

    @NotBlank(message = "The description shouldn't be blank.")
    private String descripcion;
}
