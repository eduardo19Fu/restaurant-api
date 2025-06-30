package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.model.Horario;
import com.pangosoft.restaurant.repository.IHorarioRepository;
import com.pangosoft.restaurant.service.IHorarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HorarioServiceImpl implements IHorarioService {

    private final IHorarioRepository horarioRepository;

    @Override
    public List<Horario> getHorarios() {
        return List.of();
    }

    @Override
    public Horario getHorario(int id) {
        return null;
    }

    @Override
    public Horario create(Horario horario) {
        return null;
    }

    @Override
    public Horario updae(Horario horario, int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
