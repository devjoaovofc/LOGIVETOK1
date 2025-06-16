// Conteúdo de pirrico/logivet11/Logivet11-b4e66927542ce07dce03e7e0eb18e87734d1e720/Tela/TelaLogin.java
package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Tela.database.ConexaoBD;
import Tela.TelaMenuItens;
import Tela.dao.UsuarioDAO; // Importa o novo UsuarioDAO
import Tela.model.Usuario; // Importa o novo modelo Usuario

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;

    public TelaLogin() {
        super("LogiVet - Login");

        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Painel esquerdo
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBounds(0, 0, 600, 700);
        painelEsquerdo.setBackground(Color.WHITE);
        painelEsquerdo.setLayout(null);
        add(painelEsquerdo);

        // Caminho para a imagem "logovet.png.png" agora diretamente em "/Imagens/"
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagemRedimensionada));
        logo.setBounds(150, 120, 300, 80);
        painelEsquerdo.add(logo);

        int baseY = 250;

        // Painel do campo de e-mail
        JPanel painelEmail = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painelEmail.setBounds(150, 245, 300, 35);
        painelEmail.setOpaque(false);
        painelEmail.setBackground(new Color(0xA6D5C1));
        painelEsquerdo.add(painelEmail);

        // Caminho para a imagem "gmail 1.png" agora diretamente em "/Imagens/"
        JLabel iconEmail = new JLabel(new ImageIcon(getClass().getResource("/Imagens/gmail 1.png")));
        iconEmail.setBounds(5, 5, 24, 24);
        painelEmail.add(iconEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(35, 5, 260, 24);
        campoEmail.setBorder(null);
        campoEmail.setOpaque(false);
        painelEmail.add(campoEmail);

        // Painel do campo de senha
        JPanel painelSenha = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painelSenha.setBounds(150, 300, 300, 35);
        painelSenha.setOpaque(false);
        painelSenha.setBackground(new Color(0xA6D5C1));
        painelEsquerdo.add(painelSenha);

        // Caminho para a imagem "icone_senha.png" agora diretamente em "/Imagens/"
        JLabel iconSenha = new JLabel(new ImageIcon(getClass().getResource("/Imagens/icone_senha.png")));
        iconSenha.setBounds(5, 5, 24, 24);
        painelSenha.add(iconSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(35, 5, 260, 24);
        campoSenha.setBorder(null);
        campoSenha.setOpaque(false);
        painelSenha.add(campoSenha);

        // Botão LOGIN
        JButton botaoLogin = new JButton("LOGIN") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        botaoLogin.setContentAreaFilled(false);
        botaoLogin.setFocusPainted(false);
        botaoLogin.setOpaque(true);
        botaoLogin.setBounds(150, baseY + 100, 300, 35);
        botaoLogin.setBackground(new Color(255, 127, 80));
        botaoLogin.setForeground(Color.WHITE);
        painelEsquerdo.add(botaoLogin);

        botaoLogin.addActionListener(e -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO(); // Instancia o DAO de usuário
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());
            
            // Tenta autenticar o usuário e buscar seus dados
            Usuario usuarioLogado = usuarioDAO.buscarUsuarioPorEmailESenha(email, senha);

            if (usuarioLogado != null) {
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!");
                new TelaMenuItens(usuarioLogado).setVisible(true); // Passa o usuário logado para TelaMenuItens
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Links
        JLabel cadastrar = new JLabel("Cadastre-se");
        cadastrar.setFont(new Font("Arial", Font.PLAIN, 12));
        cadastrar.setBounds(150, baseY + 150, 150, 20);
        cadastrar.setForeground(Color.BLUE);
        painelEsquerdo.add(cadastrar);
        cadastrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaCadastro();
                dispose();
            }
        });

        JLabel esqueceuSenha = new JLabel("Esqueceu a senha?");
        esqueceuSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        esqueceuSenha.setForeground(Color.BLUE);
        esqueceuSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        esqueceuSenha.setBounds(375, baseY + 150, 150, 20);
        painelEsquerdo.add(esqueceuSenha);
        esqueceuSenha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaRecuperarSenha().setVisible(true);
                dispose();
            }
        });

        // Caminho para a imagem "uniceplac.png" AGORA em "/Imagens/"
        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png")));
        logoUniceplac.setBounds(70, 550, 250, 60);
        painelEsquerdo.add(logoUniceplac);

        // Painel direito
        JPanel painelDireito = new JPanel();
        painelDireito.setBounds(600, 0, 600, 700);
        painelDireito.setLayout(null);
        add(painelDireito);

        // Caminho para a imagem "imagem_lateral.png" AGORA em "/Imagens/"
        JLabel imagemLateral = new JLabel(new ImageIcon(getClass().getResource("/Imagens/imagem_lateral.png")));
        imagemLateral.setBounds(0, 0, 600, 700);
        painelDireito.add(imagemLateral);

        setVisible(true);
    }

    // MUDANÇA: Novo método para autenticar e buscar o usuário pelo email e senha
    private boolean validarLogin() {
        // A lógica de validação agora está no ActionListener do botão.
        // Este método pode ser removido se não houver outras chamadas.
        // Mantenho apenas para compatibilidade se houver outras chamadas indiretas.
        return true; // A autenticação real é feita no ActionListener
    }
}