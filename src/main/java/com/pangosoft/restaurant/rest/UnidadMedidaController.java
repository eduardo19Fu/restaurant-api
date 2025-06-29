package com.pangosoft.restaurant.rest;

import com.pangosoft.restaurant.model.UnidadMedida;
import com.pangosoft.restaurant.service.IUnidadMedidaService;

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
@RequestMapping(value = "/restaurant/v01/api/unidades-medida")
public class UnidadMedidaController {

    private final IUnidadMedidaService unidadMedidaService;

    @GetMapping(value = "/list/get")
    public ResponseEntity<List<UnidadMedida>> listarUnidadesMedida() {
        log.info("Obteniendo listado de unidades registradas...");
        return ResponseEntity.ok(unidadMedidaService.getUnidadesMedida());
    }

    @GetMapping(value = "/find/get/{id}")
    public ResponseEntity<UnidadMedida> buscarUnidadPorId(@PathVariable Integer id) {
        log.info("Obteniendo unidad de medida registrada...");
        return ResponseEntity.ok(unidadMedidaService.getUnidadPorId(id));
    }

    @PostMapping(value = "/create/post")
    public ResponseEntity<UnidadMedida> registrarUnidadMedida(@RequestBody UnidadMedida unidadMedida) {
        log.info("Creando nueva unidad de medida...");
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadMedidaService.create(unidadMedida));
    }

    @PutMapping(value = "/edit/put/{id}")
    public ResponseEntity<UnidadMedida> editarUnidadMedida(@RequestBody UnidadMedida unidadMedida, @PathVariable Integer id) {
        log.info("Editando unidad de medida registrada con ID: {}...", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadMedidaService.update(unidadMedida, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> eliminarUnidadMedida(@PathVariable Integer id) {
        log.info("Eliminando unidad de medida registrada...");
        unidadMedidaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
