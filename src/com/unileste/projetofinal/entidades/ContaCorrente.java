package com.unileste.projetofinal.entidades;

import com.unileste.projetofinal.utilitarios.SaldoInsuficienteException;

public class ContaCorrente extends Conta{

    private double limiteChequeEspecial;

    public ContaCorrente(String numero, Cliente proprietario, double limiteChequeEspecial){
        super(numero, proprietario);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    @Override
    public void depositar(double valorDeposito) {
        if (valorDeposito < 0){
            System.out.println("ERRO: O valor de deposito deve ser superior à 0.");
        } else {
            saldo += valorDeposito;
        }
    }

    @Override
    public void sacar(double valorSaque) throws SaldoInsuficienteException {

        double valorTotal = this.saldo + this.limiteChequeEspecial;

        if (valorSaque < 0 || valorSaque == 0){
            throw new IllegalArgumentException("O valor deve ser superior a 0.");
        }

        if (valorSaque > valorTotal){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        this.saldo -= valorSaque;

        this.adicionarTransacao("Saque: " + valorSaque);

    }


    @Override
    public void transferir(Conta destino, double valorTransferencia) throws SaldoInsuficienteException {

        double valorTotal = this.saldo + this.limiteChequeEspecial;

        if (valorTotal < 0 || valorTotal == 0){
            throw new IllegalArgumentException("O valor deve ser superior a 0.");
        }

        if (valorTransferencia > valorTotal){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        destino.saldo += valorTransferencia;

        this.adicionarTransacao("Transação: " + valorTransferencia);

    }

    @Override
    public String toString(){
        return "ContaPoupanca [" +
                "Número: " + getNumero() +
                ", Saldo: R$ " + getSaldo() +
                ", Limite Cheque Especial: " + this.limiteChequeEspecial +
                ", Proprietário: " + getProprietario().getNome() +
                "]";
    }
}
