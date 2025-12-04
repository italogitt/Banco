package com.unileste.projetofinal.gui;

import javax.swing.*;
import java.awt.*;
import com.unileste.projetofinal.operacoes.BancoService;

public class MainFrame extends JFrame {
    private BancoService banco;

    public MainFrame(BancoService banco) {
        this.banco = banco;

        setTitle("Sistema Bancário - GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Clientes", new ClientePanel(banco));
        abas.addTab("Contas", new ContaPanel(banco));
        abas.addTab("Operações", new OperacoesPanel(banco));

        add(abas);

        setVisible(true);
    }
}

