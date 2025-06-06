package com.pangosoft.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = -8163049468796289382L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotNull(message = "The customer's name cannot be null.")
    @NotBlank(message = "The customer's name cannot be blank.")
    @Size(min = 6, max = 100, message = "Customer {validatedValue} Invalid. The customer's name should be between {min} and {max} characters.")
    private String nombre;

    @NotBlank(message = "NIT {validatedValue} Invalid.  The customer's NIT shouldn't be blank.")
    private String nit;

    @NotNull(message = "The customer's address cannot be null.")
    @NotBlank(message = "The customer's address cannot be blank.")
    @Size(min = 5, max = 150, message = "Address {validatedValue} Invalid.  The customer's address should be between {min} and {max} characters.")
    private String direccion;

    private LocalDateTime fechaCreacion;

    @PrePersist
    public void configFechaRegistro() {
        if(fechaCreacion == null) { fechaCreacion = LocalDateTime.now(); }
    }
}
