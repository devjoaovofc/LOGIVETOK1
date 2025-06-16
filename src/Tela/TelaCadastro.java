/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// src/Prontuario/gui/LogVetProntuarioGUI.java

package Tela;

import Tela.database.ConexaoBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TelaCadastro extends JFrame {

    private JTextField campoNome, campoEmail;
    private JComboBox<String> comboFuncao;
    private JPasswordField campoSenha;

    public TelaCadastro() {
        setTitle("LogiVet - Cadastro");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Logo LogiVet - Caminho para a imagem "logovet.png.png" AGORA em "/Imagens/"
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagemRedimensionada));
        logo.setBounds(475, 20, 250, 100);
        add(logo);

        int baseX = 450, larguraCampo = 300, alturaCampo = 32, espacamentoVertical = 50, yAtual = 140;

        // Nome - Caminho para a imagem "icone_email.png" AGORA em "/Imagens/"
        JPanel painelNome = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/icone_email.png");
        campoNome = criarCampoTexto(32, 5, 260, 24);
        painelNome.add(campoNome);
        add(painelNome);
        yAtual += espacamentoVertical;

        // Email - Caminho para a imagem "gmail 1.png" AGORA em "/Imagens/"
        JPanel painelEmail = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/gmail 1.png");
        campoEmail = criarCampoTexto(32, 5, 260, 24);
        painelEmail.add(campoEmail);
        add(painelEmail);
        yAtual += espacamentoVertical;

        // Função - Caminho para a imagem "mala.png" AGORA em "/Imagens/"
        JPanel painelFuncao = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/mala.png");
        String[] funcoes = {"Estoquista"};
        comboFuncao = new JComboBox<>(funcoes);
        comboFuncao.setBounds(32, 5, 260, 24);
        comboFuncao.setBackground(new Color(200, 240, 230));
        comboFuncao.setBorder(null);
        comboFuncao.setSelectedItem(null);
        painelFuncao.add(comboFuncao);
        add(painelFuncao);
        yAtual += espacamentoVertical;

        // Senha - Caminho para a imagem "icone_senha.png" AGORA em "/Imagens/"
        JPanel painelSenha = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/icone_senha.png");
        campoSenha = new JPasswordField();
        campoSenha.setBounds(32, 5, 260, 24);
        campoSenha.setOpaque(false);
        campoSenha.setBorder(null);
        painelSenha.add(campoSenha);
        add(painelSenha);
        yAtual += espacamentoVertical;

        // Botão
        JButton botaoCadastrar = new JButton("CADASTRAR") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        botaoCadastrar.setBounds(baseX + 30, yAtual, 240, 40);
        botaoCadastrar.setBackground(new Color(242, 129, 72));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.setContentAreaFilled(false);
        botaoCadastrar.setOpaque(true);
        add(botaoCadastrar);

        botaoCadastrar.addActionListener(this::cadastrarUsuario);

        setVisible(true);
    }

    private void cadastrarUsuario(ActionEvent e) {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String funcao = (String) comboFuncao.getSelectedItem();
        String senha = new String(campoSenha.getPassword());

        if (nome.isEmpty() || email.isEmpty() || funcao == null || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação de senha
        if (!senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.,_\\-@#$%&*?!])[A-Za-z\\d.,_\\-@#$%&*?!]{8,15}$")) {
            JOptionPane.showMessageDialog(this,
                "<html>A senha deve conter:<br>- De 8 a 15 caracteres<br>- Pelo menos uma letra maiúscula<br>- Pelo menos uma letra minúscula<br>- Pelo menos um número<br>- Pelo menos um caractere especial: . , _ - @ # $ % & * ? !</html>",
                "Senha inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO usuarios (nome, email, funcao, senha) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, funcao);
            stmt.setString(4, senha);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
            new TelaLogin();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JPanel criarPainelCampo(int x, int y, int largura, int altura, String caminhoIcone) {
        JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painel.setBounds(x, y, largura, altura);
        painel.setOpaque(false);
        painel.setBackground(new Color(200, 240, 230));
        // Usa o caminhoIcone passado, que agora é absoluto dentro do classpath
        JLabel icone = new JLabel(new ImageIcon(getClass().getResource(caminhoIcone)));
        icone.setBounds(5, 5, 24, 24);
        painel.add(icone);
        return painel;
    }

    private JTextField criarCampoTexto(int x, int y, int largura, int altura) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBorder(null);
        return campo;
    }
}