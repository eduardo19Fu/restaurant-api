package com.pangosoft.restaurant.rest;

import com.pangosoft.restaurant.model.Bebida;
import com.pangosoft.restaurant.model.enums.EstadoPlatoEnum;
import com.pangosoft.restaurant.service.IBebidaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/restaurant/v01/api/bebidas")
public class BebidaController {

    private final IBebidaService bebidaService;

    @GetMapping(value = "/list/get")
    public ResponseEntity<List<Bebida>> listarBebidas() {
        log.info("Listado de Bebidas...");
        return ResponseEntity.ok(bebidaService.getBebidas());
    }

    @GetMapping(value = "/list/get/{estado}")
    public ResponseEntity<List<Bebida>> listarBebidasPorEstado(@PathVariable String estado) {
        log.info("Listado de Bebidas por estado...");
        return ResponseEntity.ok(bebidaService.getBebidasPorEstado(EstadoPlatoEnum.valueOf(estado)));
    }

    @GetMapping(value = "/find/get/{id}")
    public ResponseEntity<Bebida> buscarBebidaPorId(@PathVariable Integer id) {
        log.info("Buscando Bebida con ID {}...", id);
        return ResponseEntity.ok(bebidaService.getBebidaPorId(id));
    }

    @PostMapping(value = "/create/post")
    public ResponseEntity<Bebida> crearBebida(@RequestBody Bebida bebida) {
        log.info("Creando Bebida...");
        return ResponseEntity.status(HttpStatus.CREATED).body(bebidaService.create(bebida));
    }

    @PutMapping(value = "/edit/put/{id}")
    public ResponseEntity<Bebida> actualizarBebida(@RequestBody Bebida bebida, @PathVariable Integer id) {
        log.info("Actualizando Bebida con ID {}...", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(bebidaService.getBebidaPorId(id));
    }
}
