package com.pangosoft.restaurant.rest;

import com.pangosoft.restaurant.model.Plato;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;
import com.pangosoft.restaurant.service.IPlatoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/restaurant/v01/api/platos")
public class PlatoController {

    private final IPlatoService platoService;

    @GetMapping(value = "/list/get")
    public ResponseEntity<List<Plato>> listarPlatos() {
        log.info("Listado completo de platos registrados...");
        return ResponseEntity.ok(platoService.getPlatos());
    }

    @GetMapping(value = "/list/estado/get/{estado}")
    public ResponseEntity<List<Plato>> listarPlatosPorEstado(@PathVariable("estado") String estado) {
        log.info("Listado completo de platos registrados por estado...");
        return ResponseEntity.ok(platoService.getPlatosPorEstado(EstadoPlatoEnum.valueOf(estado)));
    }

    @GetMapping(value = "/find/get/{id}")
    public ResponseEntity<Plato> buscarPlatoPorId(@PathVariable Integer id) {
        log.info("Buscando plato por ID: {}", id);
        return ResponseEntity.ok(platoService.getPlato(id));
    }

    @PostMapping(value = "/create/post")
    public ResponseEntity<Plato> crearPlato(@RequestBody Plato plato) {
        log.info("Registrando nuevo plato...");
        return ResponseEntity.status(HttpStatus.CREATED).body(platoService.create(plato));
    }

    @PutMapping(value = "/edit/put/{id}")
    public ResponseEntity<Plato> editarPlato(@RequestBody Plato plato, @PathVariable Integer id) {
        log.info("Editando plato por ID: {}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(platoService.update(plato));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Integer id) {
        log.info("Intentando eliminar plato por ID: {}", id);
        platoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
