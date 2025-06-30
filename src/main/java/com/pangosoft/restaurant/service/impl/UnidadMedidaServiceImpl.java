package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NoContentException;
import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.UnidadMedida;
import com.pangosoft.restaurant.repository.IUnidadMedidaRepository;
import com.pangosoft.restaurant.service.IUnidadMedidaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnidadMedidaServiceImpl implements IUnidadMedidaService {

    private final IUnidadMedidaRepository unidadMedidaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<UnidadMedida> getUnidadesMedida() {
        log.info("Listando Unidades de Medida registradas...");

        try {
            List<UnidadMedida> unidadesMedida = unidadMedidaRepository.findAll();

            if (unidadesMedida.isEmpty()) {
                log.warn("No existe unidades de medida registradas");
                throw new NoContentException("No existen unidades de medida registradas");
            }

            log.info("Retornando Unidades de Medida registradas");
            return unidadesMedida;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de base de datos al consultar las Unidades de Medida disponibles: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error en base de datos al consultar Unidades de Medida", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UnidadMedida getUnidadPorId(Integer id) {
        log.info("Buscando Unidad de Medida registrada con ID: {}", id);

        try {
            return unidadMedidaRepository.findById(id).orElseThrow(() -> {
                log.warn("No existe unidad de medida registrada con ID: {}", id);
                return new NotFoundException("La Unidad de Medida con ID" + id + " no se encuentra registrada");
            });
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de base de datos al intentar consultar la Unidad de Medida registrada {}: {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        }
    }

    @Transactional
    @Override
    public UnidadMedida create(UnidadMedida unidadMedida) {
        log.info("Registrando Nueva Unidad de Medida");

        try {
            UnidadMedida newUnidad = unidadMedidaRepository.save(unidadMedida);
            log.info("Retornando nueva Unidad de Medida: {}", newUnidad);
            return newUnidad;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error en base de datos al intentar registar nueva Unidad de Mediada: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error en base de datos", e);
        }
    }

    @Transactional
    @Override
    public UnidadMedida update(UnidadMedida unidadMedida, Integer id) {
        log.info("Actualizando Unidad de Medida: {}", id);

        UnidadMedida unidadExistente = getUnidadPorId(id);

        try {
            unidadExistente.toBuilder()
                    .idUnidadMedida(unidadMedida.getIdUnidadMedida())
                    .unidadMedida(unidadMedida.getUnidadMedida())
                    .fechaRegistro(unidadMedida.getFechaRegistro())
                    .build();

            UnidadMedida updatedUnidad = unidadMedidaRepository.save(unidadExistente);
            log.info("Unidad de Medida fue actualizada con exito: {}", updatedUnidad);
            return updatedUnidad;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de base de datos al intentar actualizar Unidad de Medida: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de base de datos", e);
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Eliminando Unidad de Medida con ID: {}", id);

        try {
            if(!unidadMedidaRepository.existsById(id)) {
                log.warn("No existe unidad de medida registrada con ID: {}", id);
                throw new NotFoundException("No existe unidad de medida " + id + " para eliminar");
            }

            unidadMedidaRepository.deleteById(id);
            log.info("Unidad de Medida eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de base de datos al intentar eliminar Unidad de Medida: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error a nivel de base de datos al intentar eliminar unidad de medida", e);
        }
    }
}
