package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Bebida;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBebidaRepository extends JpaRepository<Bebida, Integer> {
    List<Bebida> findAllByEstado(EstadoPlatoEnum estado);
}
