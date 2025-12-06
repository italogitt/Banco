package com.unileste.projetofinal.dao;

import com.unileste.projetofinal.entidades.Conta;

public interface ContaDAO {
    void salvar(Conta conta);
    Conta buscarPorNumero(String numero);
    void atualizarSaldo(Conta conta);
}
