package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NoContentException;
import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.Categoria;
import com.pangosoft.restaurant.repository.ICategoriaRepository;
import com.pangosoft.restaurant.service.ICategoriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaServiceImpl implements ICategoriaService {

    private final ICategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Categoria> getCategorias() {
        log.info("Buscando listado de categorias...");

        try {
            List<Categoria> categorias = categoriaRepository.findAll();

            if(categorias.isEmpty()) {
                log.info("Lista de categorias no encontrada");
                throw new NoContentException("No existen categorías registradas");
            }

            log.info("Retornando listado de categorias obtenido");
            return categorias;
        } catch (DataAccessException e) {
            log.error("Error a nivel de base de datos al intentar consultar las categorias registradas: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error a nivel de base de datos", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Categoria getCategoria(int id) {
        log.info("Buscando una categoria registrada con ID: {}", id);

        try {
            return categoriaRepository.findById(id).orElseThrow(() -> {
                log.warn("Categoria con ID {} no encontrada", id);
                return new NotFoundException("Categoría con ID " + id + " no encontrada");
            });
        } catch (DataAccessException e) {
            log.error("Error al buscar categoría con ID {} : {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error a nivel de base de datos", e);
        }
    }

    @Transactional
    @Override
    public Categoria create(Categoria categoria) {
        log.info("Registrando nueva categoría: {}", categoria);

        try {
            return categoriaRepository.save(categoria);
        } catch (DataAccessException e) {
            log.error("Error al registrar categoria: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error a nivel de base de datos al registrar categoria", e);
        }
    }

    @Transactional
    @Override
    public Categoria update(Categoria categoria, Integer id) {
        log.info("Actualizando categoría ID: {}", id);

        Categoria categoriaExistente = getCategoria(id);

        try {
            Categoria actualizada = categoriaExistente.toBuilder()
                    .nombre(categoria.getNombre())
                    .descripcion(categoria.getDescripcion())
                    .build();

            Categoria saved = categoriaRepository.save(actualizada);
            log.info("Categoria actualizada con exito");
            return saved;
        } catch (DataAccessException e) {
            log.error("Error al actualizar categoria: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al actualizar categoria", e);
        }
    }
}
