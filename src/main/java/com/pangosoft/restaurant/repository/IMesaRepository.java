package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMesaRepository extends JpaRepository<Mesa, Integer> {
}
