package com.pangosoft.restaurant.service.impl;

import com.pangosoft.restaurant.error.exceptions.NotFoundException;
import com.pangosoft.restaurant.model.Cliente;
import com.pangosoft.restaurant.repository.IClienteRepository;
import com.pangosoft.restaurant.service.IClienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> getClientes() {
        log.info("Listando clientes registrados...");
        List<Cliente> clientes = null;

        try {
            clientes = clienteRepository.findAll();

            if (!clientes.isEmpty()) {
                log.info("Devolviendo listado de clientes registrados...");
                return clientes;
            }

            log.warn("No existen clientes registrados");
            return clientes;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido una excepcion a nivel de Base de Datos al intentar consultar listado de clientes: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido una excepcion a nivel de Base de Datos", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente getCliente(long id) {
        log.info("Obteniendo cliente registrado con ID: {}", id);

        try {
            return clienteRepository.findById(id).orElseThrow(() -> {
                log.warn("No existe un cliente con ID: {}", id);
                return new NotFoundException("No existe un cliente con ID: " + id);
            });
        } catch (DataAccessException e) {
            log.error("Ha ocurrido una excepcion a nivel de Base de Datos al intentar consultar cliente con ID: {}, {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido una excepcion a nivel de Base de Datos al intentar consultar cliente con ID: "
                    .concat(String.valueOf(id)), e);
        }
    }

    @Transactional
    @Override
    public Cliente create(Cliente cliente) {
        log.info("Registrando cliente nuevo...");
        Cliente newCliente = null;

        try {
            newCliente = clienteRepository.save(cliente);
            log.info("Retornando nuevo cliente registrado con ID: {}", newCliente.getIdCliente());
            return newCliente;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al tratar de registrar el nuevo cliente: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException(("Ha ocurrido un error a nivel de Base de Datos " +
                    "al tratar de registrar el nuevo cliente: ").concat(e.getMessage()), e);
        }
    }

    @Transactional
    @Override
    public Cliente update(Cliente cliente, Long id) {
        log.info("Buscando clienteExistente registrado con ID: {}", cliente.getIdCliente());

        Cliente clienteExistente = null;
        Cliente clienteActualizado = null;

        try {
            clienteExistente = getCliente(id);

            if(clienteExistente != null) {
                log.info("Actualizando clienteExistente con ID: {}", clienteExistente.getIdCliente());
                clienteActualizado = clienteExistente.toBuilder()
                        .nombre(cliente.getNombre().toUpperCase())
                        .nit(cliente.getNit().toUpperCase())
                        .direccion(cliente.getDireccion().toUpperCase())
                        .build();

                clienteRepository.save(clienteActualizado);
            }

            log.info("Cliente actualizado...");
            return clienteActualizado;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al intentar actualizar el cliente: {}, {}", cliente.getIdCliente(), e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de Base de Datos al intentar actualizar el cliente", e);
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        log.info("Eliminando cliente con ID: {}", id);

        try {
            if(!clienteRepository.existsById(id)) {
                log.warn("No se encontró el cliente con ID: {}", id);
                throw new NotFoundException("No se encontró el cliente para eliminarse");
            }

            clienteRepository.deleteById(id);
            log.info("Cliente con ID: {}, eliminado", id);
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al intentar elimnar el cliente: {}, {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error al intentar eliminar el cliente", e);
        }
    }
}
