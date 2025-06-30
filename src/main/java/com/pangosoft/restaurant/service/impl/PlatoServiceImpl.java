package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.Ingrediente;
import com.pangosoft.restaurant.model.Plato;
import com.pangosoft.restaurant.model.UnidadMedida;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;
import com.pangosoft.restaurant.repository.IPlatoRepository;
import com.pangosoft.restaurant.service.ICategoriaService;
import com.pangosoft.restaurant.service.IHorarioService;
import com.pangosoft.restaurant.service.IIngredienteService;
import com.pangosoft.restaurant.service.IPlatoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatoServiceImpl implements IPlatoService {

    private final IPlatoRepository platoRepository;
    private final IIngredienteService ingredienteService;
    private final ICategoriaService categoriaService;
    private final IHorarioService horarioService;

    @Transactional(readOnly = true)
    @Override
    public List<Plato> getPlatos() {
        log.info("Obteniendo listado de platos registrados");
        List<Plato> platos = null;
        try {
            platos = platoRepository.findAll();
            if(!platos.isEmpty()) {
                log.info("Retornando listado de platos registrados");
                return platos;
            } else {
                log.warn("No existen platos registrados");
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error a nivel de base de datos al obtener la lista de platos: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al intentar obtener la lista de platos", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener la lista de platos: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Plato> getPlatosPorEstado(EstadoPlatoEnum estado) {
        log.info("Obteniendo listado de platos en estado: {}", estado);
        List<Plato> platos = null;
        try {
            platos = platoRepository.findAllByEstado(estado);
            if(!platos.isEmpty()) {
                log.info("Retornando listado de platos registrados");
                return platos;
            } else {
                log.warn("No existen platos registrados");
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error a nivel de base de datos al obtener la lista de platos por estado: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al intentar obtener la lista de platos por estado", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener la lista de platos por estado: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Plato getPlato(int id) {
        log.info("Buscando plato con ID: {}", id);

        try {
            return platoRepository.findById(id).orElseThrow(() -> {
                log.warn("No existen platos registrados con el ID: {}", id);
                return new NotFoundException("No existen platos registrados con el ID: ".concat(String.valueOf(id)));
            });
        } catch (DataAccessException e) {
            log.error("Error al obtener plato con ID: {}, {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error a nivel de base de datos al intentar obtener plato", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener plato con ID: {}, {}", id, e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional
    @Override
    public Plato create(Plato plato) {
        log.info("Creando nuevo plato: {}", plato.getNombre());

        try {
            // Si no se envía estado, ponemos DISPONIBLE por defecto
            if (plato.getEstado() == null) {
                plato.setEstado(EstadoPlatoEnum.DISPONIBLE);
            }

            // Procesar ingredientes:
            // 1) copiar los detalles entrantes
            // 2) limpiar la colección para evitar estados inconsistentes
            // 3) volver a poblarla con entidades gestionadas
            var detallesEntrantes = new HashSet<>(plato.getIngredientes());
            plato.getIngredientes().clear();

            for (var detalle : detallesEntrantes) {
                Integer idIng = detalle.getIngrediente().getIdIngrediente();
                Ingrediente ing = ingredienteService.getIngrediente(idIng);

                BigDecimal cantidad = detalle.getCantidad();
                UnidadMedida unidadMedida = detalle.getUnidadMedida();
                plato.agregarIngrediente(ing, cantidad, unidadMedida);
            }

            // 2) Categorías
            var detallesCat = new HashSet<>(plato.getCategorias());
            plato.getCategorias().clear();
            for (var pc : detallesCat) {
                Integer idCat = pc.getCategoria().getIdCategoria();
                var cat = categoriaService.getCategoria(idCat);
                plato.agregarCategoria(cat);
            }

            // 3) Horarios
            var detallesHr = new HashSet<>(plato.getHorarios());
            plato.getHorarios().clear();
            for (var ph : detallesHr) {
                Integer idHr = ph.getHorario().getIdHorario();
                var hr = horarioService.getHorario(idHr);
                plato.agregarHorario(hr);
            }

            Plato newPlato = platoRepository.save(plato);
            log.info("Plato creado correctamente con ID: {}", newPlato.getIdPlato());
            return newPlato;

        } catch (DataAccessException e) {
            log.error("Error en Base de Datos al crear plato: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al crear plato", e);
        } catch (Exception e) {
            log.error("Error inesperado al crear plato: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }

    @Transactional
    @Override
    public Plato update(Plato plato) {
        log.info("Actualizando plato con ID: {}", plato.getIdPlato());

        try {
            // Verificamos que exista
            Plato platoExistente = getPlato(plato.getIdPlato());

            // Actualizar campos simples
            platoExistente.setNombre(plato.getNombre());
            platoExistente.setDescripcion(plato.getDescripcion());
            platoExistente.setPrecio(plato.getPrecio());
            platoExistente.setEstado(plato.getEstado());

            // Reemplazar ingredientes: limpiar y volver a agregar
            platoExistente.getIngredientes().clear();
            for (var detalle : plato.getIngredientes()) {
                Integer idIng = detalle.getIngrediente().getIdIngrediente();
                Ingrediente ing = ingredienteService.getIngrediente(idIng);

                platoExistente.agregarIngrediente(ing, detalle.getCantidad(), detalle.getUnidadMedida());
            }

            // 2) Categorías
            platoExistente.getCategorias().clear();
            for (var pc : plato.getCategorias()) {
                Integer idCat = pc.getCategoria().getIdCategoria();
                var cat = categoriaService.getCategoria(idCat);
                platoExistente.agregarCategoria(cat);
            }

            // 3) Horarios
            platoExistente.getHorarios().clear();
            for (var ph : plato.getHorarios()) {
                Integer idHr = ph.getHorario().getIdHorario();
                var hr = horarioService.getHorario(idHr);
                platoExistente.agregarHorario(hr);
            }

            Plato platoUpdated = platoRepository.save(platoExistente);
            log.info("Plato actualizado correctamente con ID: {}", platoUpdated.getIdPlato());
            return platoUpdated;

        } catch (DataAccessException dae) {
            log.error("Error en Base de Datos al actualizar plato ID {}: {}", plato.getIdPlato(), dae.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al actualizar plato", dae);

        } catch (Exception ex) {
            log.error("Error inesperado al actualizar plato ID {}: {}", plato.getIdPlato(), ex.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", ex);
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        log.info("Eliminando plato con ID: {}", id);

        try {
            if(!platoRepository.existsById(id)) {
                log.warn("No existen platos registrados con ID: {}", id);
                throw new NotFoundException("No existen platos registrados con ID: ".concat(String.valueOf(id)));
            }

            platoRepository.deleteById(id);
            log.info("Plato eliminado con éxito...");
        } catch (DataAccessException e) {
            log.error("Error en Base de Datos al eliminar plato {}: {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Error al eliminar plato", e);
        } catch (Exception e) {
            log.error("Error inesperado al eliminar plato {}: {}", id, e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado", e);
        }
    }
}
