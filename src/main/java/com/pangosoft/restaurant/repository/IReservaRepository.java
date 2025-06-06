package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
}
