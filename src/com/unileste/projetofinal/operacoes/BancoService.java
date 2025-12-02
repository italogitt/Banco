package com.unileste.projetofinal.operacoes;

import com.unileste.projetofinal.entidades.Cliente;
import com.unileste.projetofinal.entidades.Conta;
import com.unileste.projetofinal.entidades.ContaCorrente;
import com.unileste.projetofinal.entidades.ContaPoupanca;
import com.unileste.projetofinal.utilitarios.ClienteNaoEncontradoException;
import com.unileste.projetofinal.utilitarios.ContaNaoEncontradaException;
import com.unileste.projetofinal.utilitarios.SaldoInsuficienteException;

import java.util.HashMap;
import java.util.Map;

public class BancoService {

    private String nomeBanco;
    private Map<String, Cliente> clientes;
    private Map<String, Conta> contas;
    private int proximoNumeroConta;

    public BancoService(String nomeBanco){

        this.nomeBanco = nomeBanco;
        this.clientes = new HashMap<>();
        this.contas = new HashMap<>();
        this.proximoNumeroConta = 0;

    }

    public void cadastrarCliente(Cliente cliente){
        if(this.clientes.containsKey(cliente.getCpf())){
            throw new IllegalArgumentException("Já existe um cliente com esse CPF");
        }

        this.clientes.put(cliente.getCpf(), cliente);
    }


    public Conta abrirConta(Cliente cliente, String tipoConta, double parametros) throws ClienteNaoEncontradoException{

        if(cliente == null || !this.clientes.containsKey(cliente.getCpf())){
            throw new ClienteNaoEncontradoException("Cliente não encontrado.");
        }


        String numeroContaGerado = String.valueOf(this.proximoNumeroConta);
        this.proximoNumeroConta++;


        Conta novaConta = null;


        if(tipoConta.equalsIgnoreCase("Corrente")){
            novaConta = new ContaCorrente(numeroContaGerado, cliente, parametros);

        } else if (tipoConta.equalsIgnoreCase("Poupanca")) {
            novaConta = new ContaPoupanca(numeroContaGerado, cliente, parametros);

        } else {
            throw new IllegalArgumentException("Tipo de conta inválido.");
        }


        this.contas.put(numeroContaGerado, novaConta);

        System.out.println("Nova Conta " + tipoConta + " criada com sucesso!");

        return novaConta;
    }


    public Cliente buscarCliente(String cpf)throws ClienteNaoEncontradoException{

        if(cpf == null || cpf.trim().isEmpty()){
            throw new IllegalArgumentException("CPF inválido para busca.");
        }

        Cliente clienteEncontrado = this.clientes.get(cpf);

        if(clienteEncontrado == null){
            throw new ClienteNaoEncontradoException("Cliente de CPF: " + cpf + " não foi encontrado.");
        }

        return clienteEncontrado;
    }


    public Conta buscarConta(String numeroConta) throws ContaNaoEncontradaException{

        if(numeroConta == null || numeroConta.trim().isEmpty()){
            throw new IllegalArgumentException("Numero de conta inválido para busca.");
        }

        Conta contaEncontrada = this.contas.get(numeroConta);

        if(contaEncontrada == null){
            throw new ContaNaoEncontradaException("Conta de número: " + numeroConta + " não foi encontrada.");
        }

        return contaEncontrada;

    }

    public void realizarDeposito(String numeroConta, double valorDeposito) throws ContaNaoEncontradaException{

        if(numeroConta == null || numeroConta.trim().isEmpty()){
            throw new IllegalArgumentException("Numero de conta inválido para depósito");
        }

        if(valorDeposito < 0 || valorDeposito == 0.0){
            throw new IllegalArgumentException("Valor inválido para depósito.");
        }

        Conta contaEncontrada = this.contas.get(numeroConta);

        if(contaEncontrada == null){
            throw new ContaNaoEncontradaException("Conta de numero " + numeroConta + " não foi encontrada.");
        }

        contaEncontrada.depositar(valorDeposito);

    }


    public void realizarSaque(String numeroConta, double valorSaque)throws ContaNaoEncontradaException, SaldoInsuficienteException{

        if(numeroConta == null || numeroConta.trim().isEmpty()){
            throw new IllegalArgumentException("Numero de conta inválido para saque");
        }

        if(valorSaque <= 0){
            throw new IllegalArgumentException("Valor inválido para saque");
        }

        Conta contaEncontrada = this.contas.get(numeroConta);

        if(contaEncontrada == null){
            throw new ContaNaoEncontradaException("Conta de numero " + numeroConta + " não foi encontrada.");
        }


        contaEncontrada.sacar(valorSaque);

    }

    public void realizarTransferencia(String contaOrigem, String contaDestino, double valorTranferencia)throws ContaNaoEncontradaException, SaldoInsuficienteException{

        if(contaOrigem == null || contaOrigem.trim().isEmpty()){
            throw new IllegalArgumentException("Conta de Origem inválida para transferência");
        }

        if(contaDestino == null || contaDestino.trim().isEmpty()){
            throw new IllegalArgumentException("Conta de destino inválida para tranferência");
        }

        if(valorTranferencia < 0 || valorTranferencia == 0.0){
            throw new IllegalArgumentException("Valor inválido para tranferência");
        }

        Conta contaOrigemEncontrada = this.contas.get(contaOrigem);
        Conta contaDestinoEncontrada = this.contas.get(contaDestino);

        if(contaOrigemEncontrada == null){
            throw new ContaNaoEncontradaException("Conta de origem inválida");
        }

        if(contaDestinoEncontrada == null){
            throw new ContaNaoEncontradaException("Conta de destino inválida");
        }

        contaOrigemEncontrada.transferir(contaDestinoEncontrada, valorTranferencia);

    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public Map<String, Conta> getContas() {
        return contas;
    }
}
