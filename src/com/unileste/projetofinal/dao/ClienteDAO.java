package com.unileste.projetofinal.dao;

import com.unileste.projetofinal.entidades.Cliente;

public interface ClienteDAO {
    void salvar(Cliente cliente);
    Cliente buscarPorCpf(String cpf);
}
