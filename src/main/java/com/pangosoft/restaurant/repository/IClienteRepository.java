package com.pangosoft.restaurant.repository;

import com.pangosoft.restaurant.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
