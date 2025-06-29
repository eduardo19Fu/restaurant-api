package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NoContentException;
import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.Bebida;
import com.pangosoft.restaurant.model.Categoria;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;
import com.pangosoft.restaurant.repository.IBebidaRepository;
import com.pangosoft.restaurant.repository.ICategoriaRepository;
import com.pangosoft.restaurant.service.IBebidaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BebidaServiceImpl implements IBebidaService {

    private final IBebidaRepository bebidaRepository;

    private final ICategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Bebida> getBebidas() {
        log.info("Obteniendo listado completo de bebidas...");

        try {
            List<Bebida> bebidas = bebidaRepository.findAll();

            if(!bebidas.isEmpty()) {
                log.info("Devolviendo listado de bebidas obtenido...");
            } else {
                log.warn("No existen bebidas registradas...");
                throw new NoContentException("No existen bebidas registradas");
            }

            return bebidas;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error en base de datos al tratar de consultar las bebidas: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error al consultar las bebidas", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Bebida> getBebidasPorEstado(EstadoPlatoEnum estado) {
        log.info("Obteniendo listado completo de bebidas por estado...");

        try {
            List<Bebida> bebidas = bebidaRepository.findAllByEstado(estado);

            if(!bebidas.isEmpty()) {
                log.info("Devolviendo listado de bebidas en estado {} obtenido...", estado);
            } else {
                log.warn("No existen bebidas en estado {} registradas...", estado);
                throw new NoContentException("No existen bebidas registradas");
            }

            return bebidas;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error en base de datos al tratar de consultar las bebidas de estado {}: {}", estado, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error al consultar las bebidas", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Bebida getBebidaPorId(int id) {
        log.info("Obteniendo bebida por ID: {}", id);

        try {
            return bebidaRepository.findById(id).orElseThrow(() -> {
                log.error("No existen bebida registrada con ID: {}...", id);
                return new NotFoundException("No existe bebida registrada con ID: ".concat(String.valueOf(id)));
            });
        } catch (DataAccessException e) {
            log.error("Error al intentar consultar la bebida con ID: {}", id);
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al consultar bebida", e);
        }
    }

    @Transactional
    @Override
    public Bebida create(Bebida bebida) {
        log.info("Registrando nueva bebida...{}", bebida);

        try {

            if (bebida.getEstado() == null) {
                bebida.setEstado(EstadoPlatoEnum.DISPONIBLE);
            }

            // Capturamos las categorías entrantes y limpiamos la colección
            var categoriasEntrantes = new HashSet<>(bebida.getCategorias());
            bebida.getCategorias().clear();

            // Para cada categoría que viene en el DTO, cargamos la entidad y la agregamos
            for (var cb : categoriasEntrantes) {
                Integer catId = cb.getCategoria().getIdCategoria();
                Categoria cat = categoriaRepository.findById(catId)
                        .orElseThrow(() -> {
                            log.error("No existen categoria con ID: {}...", catId);
                            return new NotFoundException("Categoría no encontrada: " + catId);
                        });
                bebida.agregarCategoria(cat);
            }

            Bebida newBebida = bebidaRepository.save(bebida);
            log.info("Devolviendo nueva bebida: {}", bebida.getIdBebida());
            return newBebida;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error al intentar registrar la nueva bebida: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al registrar la nueva bebida...", e);
        }
    }

    @Transactional
    @Override
    public Bebida update(Integer id, Bebida bebida) {
        log.info("Actualizando la bebida con ID: {}", id);
        Bebida bebidaExistente = getBebidaPorId(id);

        try {
            Bebida bebidaActualizada = null;

            bebidaActualizada = bebidaExistente.toBuilder()
                .descripcion(bebida.getDescripcion())
                .nombre(bebida.getNombre())
                .precio(bebida.getPrecio())
                .estado(bebida.getEstado())
                .imagen(bebida.getImagen())
                .categorias(new HashSet<>())
                .build();

            for (var cb : bebida.getCategorias()) {
                Integer categoriaId = cb.getCategoria().getIdCategoria();
                Categoria cat = categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> {
                            log.error("No existen categoria con ID: {}...", categoriaId);
                            return new NotFoundException("Categoria no encontrada: " + categoriaId);
                        });
                bebidaActualizada.agregarCategoria(cat);
            }

            Bebida bebidaGuardada = bebidaRepository.save(bebidaActualizada);
            log.info("Bebida actualizada con éxito");
            return bebidaGuardada;
        } catch (DataAccessException e) {
            log.error("Error al actualizar la bebida {}: {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error al actualizar la bebida", e);
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Eliminando bebida por ID: {}", id);

        try {
            if(!bebidaRepository.existsById(id)) {
                log.warn("No existen bebida registrada con ID: {}...", id);
                throw new NotFoundException("No existen bebida registrada con ID: ".concat(String.valueOf(id)));
            }

            bebidaRepository.deleteById(id);
            log.info("Bebida eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            log.error("Error al intentar eliminar la bebida con ID: {}", id);
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al intentar eliminar la bebida", e);
        }
    }
}
