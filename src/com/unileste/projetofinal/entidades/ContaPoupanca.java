package com.unileste.projetofinal.entidades;

import com.unileste.projetofinal.utilitarios.SaldoInsuficienteException;

public class ContaPoupanca extends Conta{

    private double taxaRendimentoMensal;


    public ContaPoupanca(String numero, Cliente proprietario, double taxaRendimentoMensal){
        super(numero, proprietario);
        this.taxaRendimentoMensal = taxaRendimentoMensal;
    }

    @Override
    public void depositar(double valorDeposito) {
        if (valorDeposito < 0){
            System.out.println("ERRO: O valor de deposito deve ser superior à 0.");
        }

        saldo += valorDeposito;

        this.adicionarTransacao("Depósito: " + valorDeposito);

    }

    @Override
    public void sacar(double valorSaque) throws SaldoInsuficienteException {
        if(valorSaque < 0 || valorSaque == 0){
            throw new IllegalArgumentException("O valor deve ser superior a 0.");
        }

        if(valorSaque > this.saldo){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        this.saldo -= valorSaque;

        this.adicionarTransacao("Saque: " + valorSaque);

    }

    @Override
    public void transferir(Conta destino, double valorTransferencia) throws SaldoInsuficienteException {
        if(valorTransferencia < 0 || valorTransferencia == 0){
            throw new IllegalArgumentException("O valor deve ser superior a 0.");
        }

        if(valorTransferencia > this.saldo){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        destino.saldo += valorTransferencia;

        this.adicionarTransacao("Transferencia: " + valorTransferencia);
    }

    public void renderJuros(){
        double rendimento = this.saldo * this.taxaRendimentoMensal;

        saldo += rendimento;
    }

    @Override
    public String toString() {
        return "ContaPoupanca [" +
                "Número: " + getNumero() +
                ", Saldo: R$ " + getSaldo() +
                ", Taxa Rendimento: " + this.taxaRendimentoMensal +
                ", Proprietário: " + getProprietario().getNome() +
                "]";
    }
}
