package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.Mesa;
import com.pangosoft.restaurant.model.enums.EstadoMesaEnum;
import com.pangosoft.restaurant.repository.IMesaRepository;
import com.pangosoft.restaurant.service.IMesaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MesaServiceImpl implements IMesaService {

    private final IMesaRepository mesaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Mesa> getMesas() {
        log.info("Listando mesasregistradas...");
        List<Mesa> mesas = null;
        try {
            mesas = mesaRepository.findAll();
            if(!mesas.isEmpty()) {
                log.info("Mesas encontradas");
                return mesas;
            } else {
                log.warn("No hay mesas registradas");
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error al consultar las mesas registradas a nivel de Base de Datos: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al consultar las mesas registradas: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado al consultar las mesas registradas", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Mesa> getMesasPorEstado(EstadoMesaEnum estado) {
        log.info("Buscando Mesas por Estado");
        List<Mesa> mesas = null;
        try {
            mesas = mesaRepository.findByEstado(estado);
            if(!mesas.isEmpty()) {
                log.info("Devolvendo mesas {}", estado);
                return mesas;
            } else {
                log.warn("No hay mesas en estado {}", estado);
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al consultar las mesas en estado: {}, {}", estado, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al consultar las mesas en estado: {}, {}", estado, e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Mesa getMesa(int id) {
        log.info("Buscando Mesa con ID {}", id);

        try {
            return mesaRepository.findById(id).orElseThrow(() -> {
                log.warn("La mesa con ID: {}, no se encuentra registrada", id);
                return new NotFoundException("La mesa con ID: ".concat(String.valueOf(id).concat(" no se encuentra registrada")));
            });
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al buscar mesa con ID: {}, {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al buscar mesa con ID: {}, {}", id, e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional
    @Override
    public Mesa craete(Mesa mesa) {
        log.info("Registrando mesa...");
        Mesa newMesa = null;

        try {
            newMesa = mesaRepository.save(mesa);
            log.info("Retornando mesa con ID: {}", newMesa.getIdMesa());
            return newMesa;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al registrar nueva mesa: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al registrar mesa: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional
    @Override
    public Mesa update(Mesa mesa) {
        log.info("Actualizando mesa con id: {}", mesa.getIdMesa());
        Mesa mesaExistente = null;
        Mesa mesaActualizada = null;

        try {
            mesaExistente = getMesa(mesa.getIdMesa());

            if(mesaExistente != null ) {
                log.info("Actualizando mesa con ID: {}", mesaExistente.getIdMesa());
                mesaActualizada = mesaExistente.toBuilder()
                        .idMesa(mesa.getIdMesa())
                        .capacidad(mesa.getCapacidad())
                        .ubicacion(mesa.getUbicacion())
                        .estado(mesa.getEstado())
                        .build();

                mesaRepository.save(mesaActualizada);
            }

            log.info("Mesa actualizada con éxito...");
            return mesaActualizada;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de base de datos al actualizar la mesa: {}, {}", mesa.getIdMesa(), e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al actualizar la mesa: {}, {}", mesa.getIdMesa(), e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Override
    public void delete(int id) {
        log.info("Eliminando mesa con ID: {}", id);

        try {
            if(!mesaRepository.existsById(id)) {
                log.warn("La mesa con ID: {}, no se encuentra registrada", id);
                throw new NotFoundException("La mesa con ID: ".concat(String.valueOf(id)).concat(" no se encuentra registrada"));
            }

            mesaRepository.deleteById(id);
            log.info("Mesa con ID: {}, ha sido eliminada con éxito", id);
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de base de datos al intentar eliminar la mesa con ID: {}, {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos al intentar eliminar la mesa", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al intentar eliminar la mesa con ID: {}, {}", id, e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }
}
