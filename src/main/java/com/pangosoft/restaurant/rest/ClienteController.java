package com.pangosoft.restaurant.rest;

import com.pangosoft.restaurant.model.Cliente;
import com.pangosoft.restaurant.service.IClienteService;

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
@RequestMapping(value = "/restaurant/v01/api/clientes")
public class ClienteController {

    private final IClienteService clienteService;

    @GetMapping(value = "/list/get")
    public ResponseEntity<List<Cliente>> listarClientes() {
        log.info("Listando clientes");
        return ResponseEntity.ok(clienteService.getClientes());
    }

    @GetMapping(value = "/find/get/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable("id") Long id) {
        log.info("Buscando cliente {}", id);
        return ResponseEntity.ok(clienteService.getCliente(id));
    }

    @PostMapping(value = "/create/post")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        log.info("Registrando Cliente...");
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(cliente));
    }

    @PutMapping(value = "/edit/put/{id}")
    public ResponseEntity<Cliente> editarCliente(@RequestBody Cliente cliente, @PathVariable Long id) {
        log.info("Editando cliente {}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.update(cliente, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.info("Eliminando cliente {}", id);
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
