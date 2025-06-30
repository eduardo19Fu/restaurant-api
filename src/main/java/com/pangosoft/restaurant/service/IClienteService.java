package com.pangosoft.restaurant.service;

import com.pangosoft.restaurant.model.Cliente;

import java.util.List;

public interface IClienteService {

    public List<Cliente> getClientes();

    public Cliente getCliente(long id);

    public Cliente create(Cliente cliente);

    public Cliente update(Cliente cliente, Long id);

    public void delete(long id);
}
