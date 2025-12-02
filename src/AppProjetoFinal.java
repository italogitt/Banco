import com.unileste.projetofinal.entidades.Cliente;
import com.unileste.projetofinal.operacoes.BancoService;
import com.unileste.projetofinal.entidades.Cliente;
import com.unileste.projetofinal.entidades.Conta;
import com.unileste.projetofinal.utilitarios.ClienteNaoEncontradoException;
import com.unileste.projetofinal.utilitarios.ContaNaoEncontradaException;
import com.unileste.projetofinal.utilitarios.SaldoInsuficienteException;

import java.util.Scanner;

public class AppProjetoFinal {

    private static BancoService banco = new BancoService("Banco");
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = 0;

        do {
            exibirMenu();
            try {
                System.out.println("Escolha uma opção: ");
                String input = sc.nextLine();
                opcao = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println("-------------------------------------------------------------");

            switch (opcao){
                case 1:
                    cadastrarCliente();
                    break;

                case 2:
                    abrirConta();
                    break;

                case 3:
                    listarClientes();
                    break;

                case 4:
                    listarContas();
                    break;

                case 5:
                    realizarDeposito();
                    break;

                case 6:
                    realizarSaque();
                    break;

                case 7:
                    realizarTransferencia();
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            if(opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                sc.nextLine();
            }
        }

        while (opcao != 0);
        sc.close();

    }

    private static void exibirMenu(){

        System.out.println("\n=== " + banco.getNomeBanco() + " - SISTEMA BANCÁRIO ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Abrir Nova Conta");
        System.out.println("3. Listar Clientes");
        System.out.println("4. Listar Contas");
        System.out.println("5. Realizar Depósito");
        System.out.println("6. Realizar Saque");
        System.out.println("7. Realizar Transferência");
        System.out.println("0. Sair");
        System.out.println("==========================================");

    }


    private static void cadastrarCliente(){
        System.out.println(">> NOVO CLIENTE");
        try {

            System.out.println("Nome Completo: ");
            String nome = sc.nextLine();

            System.out.println("CPF: ");
            String cpf = sc.nextLine();

            System.out.println("Endereco: ");
            String endereco = sc.nextLine();

            Cliente novoCliente = new Cliente(nome, cpf, endereco);
            banco.cadastrarCliente(novoCliente);

            System.out.println("Cliente cadastrado com sucesso!");

        } catch (IllegalArgumentException e){

            System.err.println("Erro ao cadastrar" + e.getMessage());
            
        }
    }


    private static void abrirConta(){
        System.out.println(">> ABRIR CONTA");

        try {

            System.out.println("Digite o CPF do Titular: ");
            String cpf = sc.nextLine();

            Cliente titular = banco.buscarCliente(cpf);

            System.out.println("Cliente encontrado: " + titular.getNome());
            System.out.println("Tipo de conta (Corrente/Poupanca): ");
            String tipoConta = sc.nextLine();

            double parametro = 0.0;

            if (tipoConta.equalsIgnoreCase("Corrente")) {

                System.out.println("Limite do Cheque Especial: ");
                parametro = Double.parseDouble(sc.nextLine());
                Conta contaCriada = banco.abrirConta(titular, tipoConta, parametro);

            } else if (tipoConta.equalsIgnoreCase("Poupanca")) {

                System.out.println("Taxa de Rendimento: ");
                parametro = Double.parseDouble(sc.nextLine());
                Conta contaCriada = banco.abrirConta(titular, tipoConta, parametro);

            }
        }

        catch (ClienteNaoEncontradoException e){
            System.err.println("Erro " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.err.println("Erro nos dados informados: " + e.getMessage());
        }

    }


    private static void realizarSaque(){

        System.out.println(">> SAQUE");

        try{

            System.out.println("Nuemro da Conta: ");
            String numero = sc.nextLine();

            System.out.println("Valor do Saque: ");
            double valor = Double.parseDouble(sc.nextLine());

            banco.realizarSaque(numero, valor);
            System.out.println("Saque realizado com sucesso!");

        }

        catch (ContaNaoEncontradaException | SaldoInsuficienteException e){
            System.err.println("Erro: " + e.getMessage());
        }
        catch (IllegalArgumentException e){
            System.out.println("Dados inválidos: " + e.getMessage());
        }

    }


    private static void realizarDeposito(){

        System.out.println(">> DEPÓSITO");

        try{

            System.out.println("Nuemro da conta: ");
            String numero = sc.nextLine();

            System.out.println("Valor do depósito: ");
            double valor = Double.parseDouble(sc.nextLine());

            banco.realizarDeposito(numero, valor);
            System.out.println("Depósito realizado com sucesso!");

        } catch (ContaNaoEncontradaException e){
            System.err.println("Erro: " + e.getMessage());;
        }
        catch (IllegalArgumentException e){
            System.out.println("Dados inválidos: "  + e.getMessage());
        }

    }

    private static void realizarTransferencia(){

        System.out.println(">> TRANSFERENCIA");

        try{

            System.out.println("Conta de Origem: ");
            String origem = sc.nextLine();

            System.out.println("Conta de Destino: ");
            String destino = sc.nextLine();

            System.out.println("Valor de Transferência: ");
            double valor = Double.parseDouble(sc.nextLine());

            banco.realizarTransferencia(origem, destino, valor);
            System.out.println("Transferência realizada com sucesso!");


        }

        catch(ContaNaoEncontradaException | SaldoInsuficienteException e){
            System.out.println("Erro: " + e.getMessage());
        }
        catch (IllegalArgumentException e){
            System.out.println("Dados inválidos: " + e.getMessage());
        }

    }


    private static void listarClientes(){

        System.out.println(">> LISTA DE CLIENTES");

        if (banco.getClientes().isEmpty()){
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : banco.getClientes().values()){
                System.out.println(c);
            }
        }
    }


    private static void listarContas(){

        System.out.println(">> LISTA DE CONTAS");
        if(banco.getContas().isEmpty()){
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            for (Conta c : banco.getContas().values()){
                System.out.println(c);
            }
        }
    }

}
