package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICargoRepository extends JpaRepository<Cargo, Integer> {
}
