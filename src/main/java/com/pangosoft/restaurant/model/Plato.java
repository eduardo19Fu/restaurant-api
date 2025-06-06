package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}
