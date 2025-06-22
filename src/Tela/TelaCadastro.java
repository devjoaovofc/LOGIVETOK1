package Tela;

import Tela.database.ConexaoBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        // Logo LogiVet
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagemRedimensionada));
        logo.setBounds(475, 20, 250, 100);
        add(logo);

        int baseX = 450, larguraCampo = 300, alturaCampo = 32, espacamentoVertical = 50, yAtual = 140;

        // Nome
        JPanel painelNome = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/icone_email.png");
        campoNome = criarCampoTexto(32, 5, 260, 24);
        campoNome.setText("Digite seu nome (sem números)");
        campoNome.setForeground(Color.GRAY);
        campoNome.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoNome.getText().equals("Digite seu nome")) {
                    campoNome.setText("");
                    campoNome.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campoNome.getText().isEmpty()) {
                    campoNome.setText("Digite seu nome");
                    campoNome.setForeground(Color.GRAY);
                }
            }
        });
        painelNome.add(campoNome);
        add(painelNome);
        yAtual += espacamentoVertical;

        // Email
        JPanel painelEmail = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/gmail 1.png");
        campoEmail = criarCampoTexto(32, 5, 260, 24);
        campoEmail.setText("Digite seu e-mail");
        campoEmail.setForeground(Color.GRAY);
        campoEmail.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoEmail.getText().equals("Digite seu e-mail")) {
                    campoEmail.setText("");
                    campoEmail.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campoEmail.getText().isEmpty()) {
                    campoEmail.setText("Digite seu e-mail");
                    campoEmail.setForeground(Color.GRAY);
                }
            }
        });
        painelEmail.add(campoEmail);
        add(painelEmail);
        yAtual += espacamentoVertical;

        // Função
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

        // Senha
        JPanel painelSenha = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/icone_senha.png");
        campoSenha = new JPasswordField();
        campoSenha.setBounds(32, 5, 260, 24);
        campoSenha.setOpaque(false);
        campoSenha.setBorder(null);
        campoSenha.setText("Digite sua senha");
        campoSenha.setForeground(Color.GRAY);
        campoSenha.setEchoChar((char) 0);
        campoSenha.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(campoSenha.getPassword()).equals("Digite sua senha")) {
                    campoSenha.setText("");
                    campoSenha.setForeground(Color.BLACK);
                    campoSenha.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (new String(campoSenha.getPassword()).isEmpty()) {
                    campoSenha.setText("Digite sua senha");
                    campoSenha.setForeground(Color.GRAY);
                    campoSenha.setEchoChar((char) 0);
                }
            }
        });
        painelSenha.add(campoSenha);
        add(painelSenha);
        yAtual += espacamentoVertical;

        // Botão Cadastrar
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

        if (nome.isEmpty() || nome.equals("Digite seu nome (sem números)") ||
            email.isEmpty() || email.equals("Digite seu e-mail") ||
            senha.isEmpty() || senha.equals("Digite sua senha") ||
            funcao == null) {

            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!nome.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
            JOptionPane.showMessageDialog(this, "O nome não pode conter números ou caracteres especiais.");
            return;
        }

        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Digite um e-mail válido.");
            return;
        }

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
