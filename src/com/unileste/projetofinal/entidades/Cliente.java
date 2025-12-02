package com.unileste.projetofinal.entidades;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nome;
    private String cpf;
    private String endereco;
    private List<Conta> contas;

    public Cliente(String nome, String cpf, String endereco){

        if (nome == null){
            System.out.println("Nome vázio... Tente novamente.");
        } else {
            this.nome = nome;
        }

        if (cpf == null){
            System.out.println("CPF vazio... Tente novamente.");
        } else {
            this.cpf = cpf;
        }

        this.endereco = endereco;
        contas = new ArrayList<>();

    }



    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public void AdicionarConta(Conta conta){ contas.add(conta); }

    @Override
    public boolean equals(Object o){
        Cliente c = (Cliente)o;

        if (this.cpf.equals(c.getCpf())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "Nome: " + this.nome + "\nCpf: " + this.cpf + "\nEndereço: " + this.endereco;
    }

}
