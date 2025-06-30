package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
}
