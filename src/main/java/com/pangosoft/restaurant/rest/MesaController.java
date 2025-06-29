package com.pangosoft.restaurant.rest;

import com.pangosoft.restaurant.model.Mesa;
import com.pangosoft.restaurant.model.enums.EstadoMesaEnum;
import com.pangosoft.restaurant.service.IMesaService;

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
@RequestMapping(value = "/restaurant/v01/api/mesas")
public class MesaController {

    private final IMesaService mesaService;

    @GetMapping(value = "/list/get")
    public ResponseEntity<List<Mesa>> listarMesas() {
        log.info("Listando todas las mesas registradas...");
        return ResponseEntity.ok(mesaService.getMesas());
    }

    @GetMapping(value = "/list/estado/get/{estado}")
    public ResponseEntity<List<Mesa>> listarMesasPorEstado(@PathVariable String estado) {
        log.info("Listado mesas por estado: {}", estado);
        return ResponseEntity.ok(mesaService.getMesasPorEstado(EstadoMesaEnum.valueOf(estado)));
    }

    @GetMapping(value = "/find/get/{id}")
    public ResponseEntity<Mesa> buscarMesaPorId(@PathVariable Integer id) {
        log.info("Buscand mesa por ID: {}", id);
        return ResponseEntity.ok(mesaService.getMesa(id));
    }

    @PostMapping(value = "/create/post")
    public ResponseEntity<Mesa> registrarMesa(@RequestBody Mesa mesa) {
        log.info("Registrando mesa: {}", mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaService.craete(mesa));
    }

    @PutMapping(value = "/update/put/{id}")
    public ResponseEntity<Mesa> editarMesa(@RequestBody Mesa mesa, @PathVariable Integer id) {
        log.info("Editando mesa: {}", mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaService.update(mesa));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Integer id) {
        log.info("Eliminando mesa: {}", id);
        mesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
