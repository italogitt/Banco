package com.unileste.projetofinal.dao;

import com.unileste.projetofinal.entidades.*;

import java.sql.*;

public class ContaDAOJdbc implements ContaDAO {

    @Override
    public void salvar(Conta conta) {
        String sql = "INSERT INTO contas (numero, cpf_cliente, tipo, saldo, limite) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getNumero());
            stmt.setString(2, conta.getProprietario().getCpf());
            stmt.setString(3, conta instanceof ContaCorrente ? "corrente" : "poupanca");
            stmt.setDouble(4, conta.getSaldo());

            if (conta instanceof ContaCorrente cc) {
                stmt.setDouble(5, cc.getLimiteChequeEspecial());
            } else {
                stmt.setDouble(5, 0);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar conta: " + e.getMessage(), e);
        }
    }

    @Override
    public Conta buscarPorNumero(String numero) {
        String sql = "SELECT * FROM contas WHERE numero = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String cpf = rs.getString("cpf_cliente");
                ClienteDAO clienteDAO = new ClienteDAOJdbc();
                Cliente cli = clienteDAO.buscarPorCpf(cpf);

                if (rs.getString("tipo").equals("corrente")) {
                    return new ContaCorrente(
                            rs.getString("numero"),
                            cli,
                            rs.getDouble("limite")
                    );
                } else {
                    return new ContaPoupanca(
                            rs.getString("numero"),
                            cli
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizarSaldo(Conta conta) {
        String sql = "UPDATE contas SET saldo = ? WHERE numero = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, conta.getSaldo());
            stmt.setString(2, conta.getNumero());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo: " + e.getMessage(), e);
        }
    }
}
