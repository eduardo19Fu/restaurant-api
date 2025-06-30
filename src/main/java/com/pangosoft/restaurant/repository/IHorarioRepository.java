package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHorarioRepository extends JpaRepository<Horario, Integer> {
}
