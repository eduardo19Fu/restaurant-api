package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Categoria;

import java.util.List;

public interface ICategoriaService {

    public List<Categoria> getCategorias();

    public Categoria getCategoria(int id);

    public Categoria create(Categoria categoria);

    public Categoria update(Categoria categoria, Integer id);
}
