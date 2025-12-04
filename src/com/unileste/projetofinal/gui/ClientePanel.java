package com.unileste.projetofinal.gui;

import javax.swing.*;
import java.awt.*;
import com.unileste.projetofinal.operacoes.BancoService;
import com.unileste.projetofinal.entidades.Cliente;

public class ClientePanel extends JPanel {
    private BancoService banco;

    public ClientePanel(BancoService banco) {
        this.banco = banco;

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField campoNome = new JTextField();
        JTextField campoCpf = new JTextField();
        JTextField campoEndereco = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Cliente");
        JTextArea areaLista = new JTextArea();
        areaLista.setEditable(false);

        form.add(new JLabel("Nome:"));
        form.add(campoNome);

        form.add(new JLabel("CPF:"));
        form.add(campoCpf);

        form.add(new JLabel("Endereço:"));
        form.add(campoEndereco);

        form.add(btnCadastrar);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(areaLista), BorderLayout.CENTER);
        try {
            areaLista.setText(banco.getClientes().values().toString());
        } catch (Exception ignored) {}

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = campoNome.getText().trim();
                String cpf = campoCpf.getText().trim();
                String endereco = campoEndereco.getText().trim();

                if (nome.isEmpty() || cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Cliente c = new Cliente(nome, cpf, endereco);
                banco.cadastrarCliente(c);

                JOptionPane.showMessageDialog(this, "Cliente cadastrado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                areaLista.setText(banco.getClientes().values().toString());

                campoNome.setText("");
                campoCpf.setText("");
                campoEndereco.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
