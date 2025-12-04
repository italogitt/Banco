package com.unileste.projetofinal.gui;

import javax.swing.*;
import java.awt.*;
import com.unileste.projetofinal.operacoes.BancoService;

public class OperacoesPanel extends JPanel {
    private BancoService banco;

    public OperacoesPanel(BancoService banco) {
        this.banco = banco;

        setLayout(new GridLayout(3, 1, 10, 10));

        JPanel p1 = new JPanel(new FlowLayout());

        JTextField depConta = new JTextField(8);
        JTextField depValor = new JTextField(8);
        JButton btnDep = new JButton("Depositar");

        p1.add(new JLabel("Conta:"));
        p1.add(depConta);
        p1.add(new JLabel("Valor:"));
        p1.add(depValor);
        p1.add(btnDep);

        btnDep.addActionListener(e -> {
            try {
                String conta = depConta.getText().trim();
                String valorTxt = depValor.getText().trim();

                if (conta.isEmpty() || valorTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha conta e valor.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double valor = Double.parseDouble(valorTxt);

                banco.realizarDeposito(conta, valor);

                JOptionPane.showMessageDialog(this, "Depósito realizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                depConta.setText("");
                depValor.setText("");

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JPanel p2 = new JPanel(new FlowLayout());

        JTextField saqConta = new JTextField(8);
        JTextField saqValor = new JTextField(8);
        JButton btnSaq = new JButton("Sacar");

        p2.add(new JLabel("Conta:"));
        p2.add(saqConta);
        p2.add(new JLabel("Valor:"));
        p2.add(saqValor);
        p2.add(btnSaq);

        btnSaq.addActionListener(e -> {
            try {
                String conta = saqConta.getText().trim();
                String valorTxt = saqValor.getText().trim();

                if (conta.isEmpty() || valorTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha conta e valor.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double valor = Double.parseDouble(valorTxt);

                banco.realizarSaque(conta, valor);

                JOptionPane.showMessageDialog(this, "Saque realizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                saqConta.setText("");
                saqValor.setText("");

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JPanel p3 = new JPanel(new FlowLayout());

        JTextField trOrigem = new JTextField(8);
        JTextField trDestino = new JTextField(8);
        JTextField trValor = new JTextField(8);
        JButton btnTransf = new JButton("Transferir");

        p3.add(new JLabel("Origem:"));
        p3.add(trOrigem);
        p3.add(new JLabel("Destino:"));
        p3.add(trDestino);
        p3.add(new JLabel("Valor:"));
        p3.add(trValor);
        p3.add(btnTransf);

        btnTransf.addActionListener(e -> {
            try {
                String origem = trOrigem.getText().trim();
                String destino = trDestino.getText().trim();
                String valorTxt = trValor.getText().trim();

                if (origem.isEmpty() || destino.isEmpty() || valorTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha origem, destino e valor.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double valor = Double.parseDouble(valorTxt);

                banco.realizarTransferencia(origem, destino, valor);

                JOptionPane.showMessageDialog(this, "Transferência realizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                trOrigem.setText("");
                trDestino.setText("");
                trValor.setText("");

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        add(p1);
        add(p2);
        add(p3);
    }

}
