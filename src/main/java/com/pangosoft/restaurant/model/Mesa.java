package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.enums.EstadoMesaEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name = "mesas")
public class Mesa implements Serializable {

    @Serial
    private static final long serialVersionUID = 6762199512904038346L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMesa;

    @NotNull(message = "It is necessary to have table capacity, so its value cannot be null")
    @Min(value = 1, message = "The table capacity needs to have at least one sit available, so its value can not be zero.")
    private Integer capacidad;

    @NotNull(message = "The location can not be null.")
    @NotBlank(message = "The location must have at least one character.")
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    private EstadoMesaEnum estado;

}
