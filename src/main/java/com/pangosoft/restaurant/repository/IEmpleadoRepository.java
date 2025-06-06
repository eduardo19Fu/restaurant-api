package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
