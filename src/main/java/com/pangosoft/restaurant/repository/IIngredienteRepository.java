package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredienteRepository extends JpaRepository<Ingrediente, Integer> {
}
