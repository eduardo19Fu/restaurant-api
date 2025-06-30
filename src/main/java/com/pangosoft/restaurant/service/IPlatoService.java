package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Plato;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;

import java.util.List;

public interface IPlatoService {

    public List<Plato> getPlatos();

    public List<Plato> getPlatosPorEstado(EstadoPlatoEnum estado);

    public Plato getPlato(int id);

    public Plato create(Plato plato);

    public Plato update(Plato plato);

    public void delete(int id);
}
