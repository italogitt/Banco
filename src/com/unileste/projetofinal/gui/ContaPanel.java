package com.unileste.projetofinal.gui;

import javax.swing.*;
import java.awt.*;
import com.unileste.projetofinal.operacoes.BancoService;
import com.unileste.projetofinal.entidades.Cliente;

public class ContaPanel extends JPanel {
    private BancoService banco;

    public ContaPanel(BancoService banco) {
        this.banco = banco;

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField campoCpf = new JTextField();
        JComboBox<String> tipo = new JComboBox<>(new String[]{"corrente", "poupanca"});
        JTextField campoLimite = new JTextField();

        JButton btnAbrir = new JButton("Abrir Conta");
        JTextArea areaLista = new JTextArea();
        areaLista.setEditable(false);

        form.add(new JLabel("CPF do Cliente:"));
        form.add(campoCpf);

        form.add(new JLabel("Tipo de Conta:"));
        form.add(tipo);

        form.add(new JLabel("Limite (corrente):"));
        form.add(campoLimite);

        form.add(btnAbrir);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(areaLista), BorderLayout.CENTER);
        try {
            areaLista.setText(banco.getContas().values().toString());
        } catch (Exception ignored){}

        btnAbrir.addActionListener(e -> {
            try {
                String tf = ((String) tipo.getSelectedItem()).trim();
                String cpf = campoCpf.getText().trim();

                if (cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Digite o CPF do cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Cliente cliente;
                try {
                    cliente = banco.buscarCliente(cpf);
                } catch (Exception buscarEx) {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado: " + buscarEx.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (tf.equalsIgnoreCase("corrente")) {
                    String txtLimite = campoLimite.getText().trim();

                    if (txtLimite.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Informe o limite da conta corrente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double limite;
                    try {
                        limite = Double.parseDouble(txtLimite);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(this, "Limite inválido. Use números.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    banco.abrirConta(cliente, "corrente", limite);

                }

                JOptionPane.showMessageDialog(this, "Conta criada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                areaLista.setText(banco.getContas().values().toString());

                campoCpf.setText("");
                campoLimite.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar conta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
