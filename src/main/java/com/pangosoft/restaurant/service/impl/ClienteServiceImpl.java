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
import java.util.Optional;

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
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al intentar consultar listado de clientes: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado al intentar consultar listado de clientes: ".concat(e.getMessage()), e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente getCliente(long id) {
        log.info("Obteniendo cliente registrado con ID: {}", id);
        Optional<Cliente> cliente = null;

        try {
            cliente = clienteRepository.findById(id);

            if (cliente.isPresent()) {
                log.info("Devolviendo cliente con ID: {}", id);
                return cliente.get();
            } else {
                log.warn("No existen cliente con ID: {}", id);
                throw new NotFoundException("No existe cliente con ID: ".concat(String.valueOf(id)).concat(" registrado"));
            }
        } catch (DataAccessException e) {
            log.error("Ha ocurrido una excepcion a nivel de Base de Datos al intentar consultar cliente con ID: {}, {}", id, e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido una excepcion a nivel de Base de Datos al intentar consultar cliente con ID: "
                    .concat(String.valueOf(id)), e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al intentar consultar cliente con ID: {}, {}", id, e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado al intentar consultar cliente con ID: ".concat(String.valueOf(id)), e);
        }
    }

    @Transactional
    @Override
    public Cliente create(Cliente cliente) {
        log.info("Registrando cliente nuevo...");
        Cliente newCliente = null;

        try {
            newCliente = clienteRepository.save(cliente);
            if(newCliente != null) {
                log.info("Retornando nuevo cliente registrado con ID: {}", newCliente.getIdCliente());
                return newCliente;
            } else {
                log.error("No se ha podido crear el cliente");
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al tratar de registrar el nuevo cliente: {}", e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException(("Ha ocurrido un error a nivel de Base de Datos " +
                    "al tratar de registrar el nuevo cliente: ").concat(e.getMessage()), e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al intentar crear un nuevo cliente: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado al intentar crear un nuevo cliente", e);
        }
    }

    @Transactional
    @Override
    public Cliente update(Cliente cliente) {
        log.info("Buscando clienteExistente registrado con ID: {}", cliente.getIdCliente());

        Cliente clienteExistente = null;
        Cliente clienteActualizado = null;

        try {
            clienteExistente = clienteRepository.findById(cliente.getIdCliente()).orElse(null);

            if(clienteExistente != null) {
                log.info("Actualizando clienteExistente con ID: {}", cliente.getIdCliente());
                clienteActualizado = clienteExistente.toBuilder()
                        .idCliente(cliente.getIdCliente())
                        .nombre(cliente.getNombre())
                        .nit(cliente.getNit())
                        .direccion(cliente.getDireccion())
                        .fechaCreacion(cliente.getFechaCreacion())
                        .build();

                clienteRepository.save(clienteActualizado);
            } else {
                log.warn("El cliente para actualizar no existe...");
                throw new NotFoundException("El cliente para actualizar no existe.");
            }

            log.info("Cliente actualizado...");
            return clienteActualizado;
        } catch (DataAccessException e) {
            log.error("Ha ocurrido un error a nivel de Base de Datos al intentar actualizar el cliente: {}, {}", cliente.getIdCliente(), e.getMessage());
            throw new com.pangosoft.restaurant.error.exceptions.DataAccessException("Ha ocurrido un error a nivel de Base de Datos al intentar actualizar el cliente", e);
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al intentar actualizar el cliente: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado al intentar actualizar el cliente", e);
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
        } catch (Exception e) {
            log.error("Ha ocurrido un error inesperado al intentar eliminar el cliente: {}", e.getMessage());
            throw new RuntimeException("Ha ocurrido un error inesperado al intentar eliminar el cliente", e);
        }
    }
}
