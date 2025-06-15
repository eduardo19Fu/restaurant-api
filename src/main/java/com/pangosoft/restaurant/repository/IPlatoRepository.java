package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Plato;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlatoRepository extends JpaRepository<Plato, Integer> {

    public List<Plato> findAllByEstado(EstadoPlatoEnum estado);
}
