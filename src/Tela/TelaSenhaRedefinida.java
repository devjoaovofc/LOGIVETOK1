/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// src/Prontuario/gui/LogVetProntuarioGUI.java




package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaSenhaRedefinida extends JFrame {

    public TelaSenhaRedefinida() {
        setTitle("Senha Redefinida");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);  // fundo branco

        // Centralização horizontal helper
        int centerX = getWidth() / 2;

        // Logo LogiVet
        ImageIcon logoIcon = new ImageIcon("src/Imagens/logovet.png.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(350, 140, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(centerX - 175, 50, 350, 140);
        add(logo);

        // Gatinho
        ImageIcon gatinhoIcon = new ImageIcon("src/Imagens/gatinho.png");
        Image gatinhoImg = gatinhoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel gatinho = new JLabel(new ImageIcon(gatinhoImg));
        gatinho.setBounds(centerX - 75, 220, 150, 150);
        add(gatinho);

        // Título
        JLabel titulo = new JLabel("SUA SENHA FOI REDEFINIDA");
        titulo.setBounds(centerX - 200, 400, 400, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        // Texto explicativo
        JLabel texto = new JLabel("Clique abaixo para entrar com sua nova senha");
        texto.setBounds(centerX - 220, 440, 440, 25);
        texto.setFont(new Font("Arial", Font.PLAIN, 18));
        texto.setHorizontalAlignment(SwingConstants.CENTER);
        add(texto);

        // Botão Entrar
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(centerX - 100, 490, 200, 40);
        btnEntrar.setBackground(new Color(255, 119, 51));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 18));
        add(btnEntrar);

        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });

        // Logo Uniceplac
        ImageIcon uniceplac = new ImageIcon("src/Imagens/uniceplac.pngx");
        JLabel logoUniceplac = new JLabel(uniceplac);
        logoUniceplac.setBounds(30, 650, 160, 35);
        add(logoUniceplac);
    }
}
