package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.Ingrediente;
import com.pangosoft.restaurant.repository.IIngredienteRepository;
import com.pangosoft.restaurant.service.IIngredienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredienteServiceImpl implements IIngredienteService {

    private final IIngredienteRepository ingredienteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Ingrediente> getIngredientes() {
        log.info("Obteniendo todos los ingredientes");
        List<Ingrediente> ingredientes = null;

        try {
            ingredientes = ingredienteRepository.findAll();
            if(!ingredientes.isEmpty()) {
                log.info("Devolviendo listado de ingredientes registrados...");
                return ingredientes;
            } else {
                log.warn("No existen ingredientes registrados...");
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error a nivel de base de datos al consultar listado de ingredientes: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al consultar ingredientes", e);
        } catch (Exception e) {
            log.error("Error inesperado al consultar ingredientes: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al consultar ingredientes", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Ingrediente getIngrediente(int id) {
        log.info("Obteniendo ingrediente con ID: {}", id);

        try {
            return ingredienteRepository.findById(id).orElseThrow(() -> {
                log.warn("No existen ingrediente con ID: {}", id);
                return new NotFoundException("No existe ingrediente con ID: " + id);
            });
        } catch (DataAccessException e) {
            log.error("Error en base de datos al consultar ingrediente con ID {}: {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error en base de datos al consultar ingrediente", e);
        } catch (Exception e) {
            log.error("Error inesperado al consultar ingrediente: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al consultar ingrediente", e);
        }
    }

    @Transactional
    @Override
    public Ingrediente create(Ingrediente ingrediente) {
        log.info("Registrando nuevo ingrediente: {}", ingrediente.getNombre());
        Ingrediente newIngrediente = null;

        try {
            newIngrediente = ingredienteRepository.save(ingrediente);
            log.info("Retornando nuevo ingrediente: {}", newIngrediente.getNombre());
            return newIngrediente;
        } catch (DataAccessException e) {
            log.error("Error en base de datos al registrar ingrediente: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error en base de datos al registrar ingrediente", e);
        } catch (Exception e) {
            log.error("Error inesperado al registrar ingrediente: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al registrar ingrediente", e);
        }
    }

    @Transactional
    @Override
    public Ingrediente update(Ingrediente ingrediente) {
        log.info("Actualizando ingrediente {}", ingrediente.getIdIngrediente());

        Ingrediente ingredienteExistente = null;
        Ingrediente ingredienteUpdated = null;

        try {
            ingredienteExistente = getIngrediente(ingrediente.getIdIngrediente());
            if(ingredienteExistente != null) {
                ingredienteUpdated = ingredienteExistente.toBuilder()
                        .idIngrediente(ingrediente.getIdIngrediente())
                        .nombre(ingrediente.getNombre())
                        .stock(ingrediente.getStock())
                        .unidadMedida(ingrediente.getUnidadMedida())
                        .build();
                ingredienteRepository.save(ingredienteUpdated);
            } else {

                log.warn("El ingrediente para actualizar no existe...");
                throw new NotFoundException("El ingrediente para actualizar no existe");
            }

            log.info("Ingrediente ha sido actualizado...");
            return ingredienteUpdated;
        } catch (DataAccessException e) {
            log.error("Error en base de datos al actualizar ingrediente: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error en base de datos al actualizar ingrediente", e);
        } catch (Exception e) {
            log.error("Error inesperado al actualizar ingrediente: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al actualizar ingrediente", e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        log.info("Eliminando ingrediente con ID: {}", id);

        try {
            if(!ingredienteRepository.existsById(id)) {
                log.warn("Ingrediente con ID: {} no se encuentra registrado", id);
                throw new NotFoundException("Ingrediente no se encuentra registrado");
            }
            ingredienteRepository.deleteById(id);
            log.info("Ingrediente ha sido eliminado con éxito");
        } catch (DataAccessException e) {
            log.error("Error en base de datos al eliminar ingrediente: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error en base de datos al eliminar ingrediente", e);
        } catch (Exception e) {
            log.error("Error inesperado al eliminar ingrediente: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al eliminar ingrediente", e);
        }
    }
}
