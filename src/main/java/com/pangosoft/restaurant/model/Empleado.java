package com.pangosoft.restaurant.model;

import com.pangosoft.restaurant.model.enums.EstadoEmpleadoEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "empleados")
public class Empleado implements Serializable {

    @Serial
    private static final long serialVersionUID = 6139914791623361128L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    @NotNull
    @NotBlank(message = "The first name cannot be blank.")
    @Size(min = 6, message = "First name should have at least six characters.")
    private String primerNombre;

    @NotBlank(message = "The middle name cannot be blank.")
    @Size(min = 6, message = "Middle name should have at least six characters.")
    private String segundoNombre;

    @NotNull
    @NotBlank(message = "The last name cannot be blank.")
    @Size(min = 6, message = "Last name should have at least six characters.")
    private String apellido;

    private String telefono;

    @Email(message = "The email doesn't have the correct format")
    private String email;

    private LocalDate fechaContratacion;

    @Enumerated(EnumType.STRING)
    private EstadoEmpleadoEnum estado;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;
}
