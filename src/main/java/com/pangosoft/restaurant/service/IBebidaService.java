package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Bebida;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;

import java.util.List;

public interface IBebidaService {
    public List<Bebida> getBebidas();

    public List<Bebida> getBebidasPorEstado(EstadoPlatoEnum estado);

    public Bebida getBebidaPorId(int id);

    public Bebida create(Bebida bebida);

    public Bebida update(Integer id, Bebida bebida);

    public void delete(Integer id);
}
