package com.pangosoft.restaurant.rest;

import com.pangosoft.restaurant.model.Ingrediente;
import com.pangosoft.restaurant.service.IIngredienteService;

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
@RequestMapping(value = "/restaurant/v01/api/ingredientes")
public class IngredienteController {

    private final IIngredienteService ingredienteService;

    @GetMapping(value = "/lista/get")
    public ResponseEntity<List<Ingrediente>> listarIngredinetes() {
        log.info("Listado de Ingredientes...");
        return ResponseEntity.ok(ingredienteService.getIngredientes());
    }

    @GetMapping(value = "/find/get/{id}")
    public ResponseEntity<Ingrediente> buscarIngredientePorId(@PathVariable Integer id) {
        log.info("Buscando Ingrediente por ID: {}", id);
        return ResponseEntity.ok(ingredienteService.getIngrediente(id));
    }

    @PostMapping(value = "/create/post")
    public ResponseEntity<Ingrediente> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        log.info("Creando Ingrediente: {}", ingrediente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredienteService.create(ingrediente));
    }

    @PutMapping(value = "/edit/put/{id}")
    public ResponseEntity<Ingrediente> editarIngrediente(@RequestBody Ingrediente ingrediente, @PathVariable Integer id) {
        log.info("Editando Ingrediente: {}", ingrediente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredienteService.update(ingrediente));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable Integer id) {
        log.info("Eliminando Ingrediente: {}", id);
        ingredienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
