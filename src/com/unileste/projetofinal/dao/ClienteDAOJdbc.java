package com.unileste.projetofinal.dao;

import com.unileste.projetofinal.entidades.Cliente;

import java.sql.*;

public class ClienteDAOJdbc implements ClienteDAO {

    @Override
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (cpf, nome, endereco) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEndereco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
        }
    }
}
