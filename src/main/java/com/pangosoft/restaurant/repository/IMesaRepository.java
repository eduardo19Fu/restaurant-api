package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Mesa;
import com.pangosoft.restaurant.model.enums.EstadoMesaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMesaRepository extends JpaRepository<Mesa, Integer> {

    List<Mesa> findByEstado(EstadoMesaEnum estado);
}
