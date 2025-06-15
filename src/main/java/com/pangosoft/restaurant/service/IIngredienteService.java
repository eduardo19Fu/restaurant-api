package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Ingrediente;

import java.util.List;

public interface IIngredienteService {
    public List<Ingrediente> getIngredientes();

    public Ingrediente getIngrediente(int id);

    public Ingrediente create(Ingrediente ingrediente);

    public Ingrediente update(Ingrediente ingrediente);

    public void delete(int id);
}
