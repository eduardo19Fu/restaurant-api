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
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "unidades_medida")
public class UnidadMedida implements Serializable {

    @Serial
    private static final long serialVersionUID = -7212214660332145792L;

    @Id
    private String idUnidadMedida;

    @NotNull
    @NotBlank(message = "Measurement Unit cannot be blank.")
    private String unidadMedida;

    private LocalDateTime fechaRegistro;
}
