package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Horario;

import java.util.List;

public interface IHorarioService {
    public List<Horario> getHorarios();

    public Horario getHorario(int id);

    public Horario create(Horario horario);

    public Horario updae(Horario horario, int id);

    public void delete(int id);
}
