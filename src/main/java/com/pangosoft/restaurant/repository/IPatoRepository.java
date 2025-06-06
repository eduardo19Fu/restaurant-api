package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatoRepository extends JpaRepository<Plato, Integer> {
}
