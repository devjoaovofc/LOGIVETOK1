package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Tela.dao.UsuarioDAO;

public class TelaAlterarSenha extends JFrame {

    public TelaAlterarSenha(String email) {
        setTitle("Alterar Senha - LogiVet");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Logo LogiVet
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(475, 30, 250, 100);
        add(logo);

        // Ícone de cadeado
        ImageIcon cadeadoIcon = new ImageIcon(getClass().getResource("/Imagens/cadeado.png"));
        Image cadeadoImg = cadeadoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel cadeado = new JLabel(new ImageIcon(cadeadoImg));
        cadeado.setBounds(560, 120, 80, 80);
        add(cadeado);

        JLabel titulo = new JLabel("ALTERE SUA SENHA:");
        titulo.setBounds(520, 210, 300, 25);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo);

        // Campo Nova Senha
        JPanel painelNovaSenha = criarPainelCampo(450, 250, 300, 32, "/Imagens/cadeadoreset.png");
        JPasswordField novaSenha = criarCampoSenha(32, 5, 260, 24, "Digite sua nova senha");
        painelNovaSenha.add(novaSenha);
        add(painelNovaSenha);

        // Campo Repetir Senha
        JPanel painelRepetirSenha = criarPainelCampo(450, 300, 300, 32, "/Imagens/icone_senha.png");
        JPasswordField repetirSenha = criarCampoSenha(32, 5, 260, 24, "Repita sua nova senha");
        painelRepetirSenha.add(repetirSenha);
        add(painelRepetirSenha);

        // Botão Redefinir Senha
        JButton btnRedefinir = new JButton("REDEFINIR SENHA") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        btnRedefinir.setBounds(500, 350, 200, 40);
        btnRedefinir.setBackground(new Color(255, 119, 51));
        btnRedefinir.setForeground(Color.WHITE);
        btnRedefinir.setFocusPainted(false);
        btnRedefinir.setContentAreaFilled(false);
        btnRedefinir.setOpaque(true);
        add(btnRedefinir);

        // Ação do botão
        btnRedefinir.addActionListener(e -> {
            String senha1 = new String(novaSenha.getPassword());
            String senha2 = new String(repetirSenha.getPassword());

            if (senha1.equals("Digite sua nova senha") || senha2.equals("Repita sua nova senha") || senha1.isEmpty() || senha2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha os dois campos.");
                return;
            }

            if (!senha1.equals(senha2)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem.");
                return;
            }

            if (!senha1.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.,_\\-@#$%&*?!])[A-Za-z\\d.,_\\-@#$%&*?!]{8,15}$")) {
                JOptionPane.showMessageDialog(this,
                    "<html>A senha deve conter:<br>- De 8 a 15 caracteres<br>- Pelo menos uma letra maiúscula<br>- Pelo menos uma letra minúscula<br>- Pelo menos um número<br>- Pelo menos um caractere especial: . , _ - @ # $ % & * ? !</html>",
                    "Senha inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean sucesso = UsuarioDAO.atualizarSenha(email, senha1);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Senha redefinida com sucesso!");
                new TelaSenhaRedefinida().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar senha. Tente novamente.");
            }
        });

        // Logo Uniceplac
        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png")));
        logoUniceplac.setBounds(70, 550, 250, 60);
        add(logoUniceplac);

        setVisible(true);
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

    private JPasswordField criarCampoSenha(int x, int y, int largura, int altura, String placeholder) {
        JPasswordField campo = new JPasswordField(placeholder);
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBorder(null);
        campo.setForeground(Color.GRAY);
        campo.setEchoChar((char) 0); // Mostrar texto como normal inicialmente

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(campo.getPassword()).equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                    campo.setEchoChar('●'); // Ativa ocultação
                }
            }

            public void focusLost(FocusEvent e) {
                if (new String(campo.getPassword()).isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                    campo.setEchoChar((char) 0); // Mostra como texto
                }
            }
        });

        return campo;
    }
}
