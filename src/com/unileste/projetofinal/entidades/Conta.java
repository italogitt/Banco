package com.unileste.projetofinal.entidades;

import com.unileste.projetofinal.utilitarios.SaldoInsuficienteException;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private String numero;
    protected double saldo;
    private Cliente proprietario;
    private List<String> historicoTransacoes;


    public Conta(String numero, Cliente proprietario){
        if(proprietario == null){
            throw new IllegalArgumentException("ERRO: Dados do proprietário são inválidos.");
        }

        if(numero == null || numero.trim().isEmpty()){
            throw new IllegalArgumentException("ERRO: Numero da conta nulo ou vazio.");
        }

        this.numero = numero;
        this.proprietario = proprietario;
        double saldo = 0.0;
        this.historicoTransacoes = new ArrayList<>();

        proprietario.AdicionarConta(this);

    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public List<String> getHistoricoTransacoes() {
        return historicoTransacoes;
    }


    protected void adicionarTransacao(String descricao){
        System.out.println(descricao);
    }

    public abstract void depositar(double valor);

    public abstract void sacar(double valor) throws SaldoInsuficienteException;

    public abstract void transferir(Conta destino, double valorTransferencia) throws SaldoInsuficienteException;


    @Override
    public boolean equals(Object o) {
        Conta c = (Conta)o;

        if(this.numero.equals(c.getNumero())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "NUMERO DA CONTA: " + getNumero() + "\nSALDO ATUAL: R$" + getSaldo() + "\nDADOS PESSOAIS: " + proprietario;
    }
}
