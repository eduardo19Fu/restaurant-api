package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.UnidadMedida;

import java.util.List;

public interface IUnidadMedidaService {

    public List<UnidadMedida> getUnidadesMedida();

    public UnidadMedida getUnidadPorId(Integer id);

    public UnidadMedida create(UnidadMedida unidadMedida);

    public UnidadMedida update(UnidadMedida unidadMedida, Integer id);

    public void delete(Integer id);
}
