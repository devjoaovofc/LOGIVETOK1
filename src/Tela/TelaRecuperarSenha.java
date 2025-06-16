package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaRecuperarSenha extends JFrame {

    public TelaRecuperarSenha() {
        setTitle("LogiVet - Recuperar Senha");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Logo principal
        // Make sure the path to the image is correct.
        // Assuming 'logovet.png.png' is directly under a 'Imagens' folder in your resources.
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(475, 30, 250, 100);
        add(logo);

        JLabel instrucao = new JLabel("ESQUECEU A SENHA?");
        instrucao.setBounds(500, 140, 250, 25);
        instrucao.setFont(new Font("Arial", Font.BOLD, 18));
        add(instrucao);

        JLabel info = new JLabel("Enviaremos as instruções para você recuperar sua senha.");
        info.setBounds(410, 170, 400, 20);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        add(info);

        // Painel de email
        // Make sure the path to the image is correct.
        // Assuming 'gmail 1.png' is directly under a 'Imagens' folder in your resources.
        JPanel painelEmail = criarPainelCampo(450, 210, 300, 32, "/Imagens/gmail 1.png");
        JTextField campoEmail = criarCampoTexto(32, 5, 260, 24);
        campoEmail.setText("Digite seu email");
        
        // Add a FocusListener to clear the default text when the field gains focus
        campoEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoEmail.getText().equals("Digite seu email")) {
                    campoEmail.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campoEmail.getText().isEmpty()) {
                    campoEmail.setText("Digite seu email");
                }
            }
        });
        
        painelEmail.add(campoEmail);
        add(painelEmail);

        JButton btnEnviar = new JButton("ENVIAR") {
            // Custom painting for rounded button
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        btnEnviar.setBounds(500, 260, 200, 40);
        btnEnviar.setBackground(new Color(255, 119, 51)); // Orange color
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setContentAreaFilled(false); // Needed for custom paintComponent to work
        btnEnviar.setOpaque(true); // Make sure the background is painted
        add(btnEnviar);

        // Action listener for the "ENVIAR" button
        btnEnviar.addActionListener(e -> {
            String email = campoEmail.getText().trim();

            if (email.isEmpty() || !email.contains("@") || email.equals("Digite seu email")) {
                // Use a custom message box instead of JOptionPane.showMessageDialog for better UI control
                // For this example, we'll keep JOptionPane for simplicity, but in a real app,
                // you'd implement a custom dialog.
                JOptionPane.showMessageDialog(this, "Digite um e-mail válido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Instead of showing a message and disposing, open TelaVerificandoEmail
            // This class (TelaRecuperarSenha) will remain open while TelaVerificandoEmail does its work.
            // TelaVerificandoEmail will then handle the next step (TelaAlterarSenha or error message).
            new TelaVerificandoEmail(email);
            // Optionally, you might want to disable this frame while verification is happening
            // setEnabled(false); 
            // Or you can dispose this frame if you are sure TelaVerificandoEmail will handle everything
            // dispose(); 
            // For now, let's keep it open to show the flow clearly.
        });

        // Logo Uniceplac
        // Make sure the path to the image is correct.
        // Assuming 'uniceplac.png' is directly under a 'Imagens' folder in your resources.
        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png")));
        logoUniceplac.setBounds(70, 550, 250, 60);
        add(logoUniceplac);

        setVisible(true);
    }

    // Helper method to create a rounded panel for text fields
    private JPanel criarPainelCampo(int x, int y, int largura, int altura, String caminhoIcone) {
        JPanel painel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g); // Call super to ensure children are painted
            }
        };
        painel.setBounds(x, y, largura, altura);
        painel.setOpaque(false); // Make it non-opaque so custom painting is visible
        painel.setBackground(new Color(200, 240, 230)); // Light blue-green color

        // Load and add the icon to the panel
        // Ensure the path is correct for the icon
        ImageIcon icon = new ImageIcon(getClass().getResource(caminhoIcone));
        Image scaledIconImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel icone = new JLabel(new ImageIcon(scaledIconImage));
        icone.setBounds(5, 5, 24, 24);
        painel.add(icone);

        return painel;
    }

    // Helper method to create a transparent text field
    private JTextField criarCampoTexto(int x, int y, int largura, int altura) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false); // Make it transparent
        campo.setBorder(null); // Remove default border
        return campo;
    }
}
