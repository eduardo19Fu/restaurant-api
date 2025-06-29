package com.pangosoft.restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHorario;

    @NotBlank(message = "The description should not be empty")
    @Size(min = 5, max = 50, message = "The description must be between 3 and 50 characters.")
    private String descripcion;

    @NotNull(message = "The start time is obligatory.")
    @Column(nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "The finish time is obligatory.")
    @Column(nullable = false)
    private LocalTime horaFin;

    /**
     * Valida que la hora de inicio sea anterior a la hora de fin.
     */
    @AssertTrue(message = "La hora de inicio debe ser anterior a la hora de fin.")
    public boolean isIntervaloValido() {
        if (horaInicio == null || horaFin == null) {
            return true; // @NotNull se encarga de reportar nulls
        }
        return horaInicio.isBefore(horaFin);
    }

}
