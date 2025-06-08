package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Mesa;
import com.pangosoft.restaurant.model.enums.EstadoMesaEnum;

import java.util.List;

public interface IMesaService {

    public List<Mesa> getMesas();

    public List<Mesa> getMesasPorEstado(EstadoMesaEnum estado);

    public Mesa getMesa(int id);

    public Mesa craete(Mesa mesa);

    public Mesa update(Mesa mesa);

    public void delete(int id);
}
