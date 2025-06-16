package Tela;

import javax.swing.*;
import Tela.dao.UsuarioDAO;
import java.awt.*;

public class TelaVerificandoEmail extends JFrame {

    private JLabel lblMensagem;
    private String email;

    // Construtor corrigido com parâmetro email2
    public TelaVerificandoEmail(String email2) {
        this.email = email2; // CORREÇÃO: agora recebe corretamente o email passado

        setTitle("LogiVet - Verificando");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.WHITE);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Logo do sistema
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png")); 
        Image logoImg = logoIcon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.add(logo);

        // Texto de status
        lblMensagem = new JLabel("Verificando email...");
        lblMensagem.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensagem.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        painelCentral.add(lblMensagem);

        add(painelCentral, BorderLayout.CENTER);

        // Iniciar verificação em outra thread
        new Thread(this::verificarEmail).start();

        setVisible(true);
    }

    // Método que faz a verificação do email no banco
    private void verificarEmail() {
        try {
            Thread.sleep(2000); // Simula carregamento

            System.out.println("Verificando o email: " + email); // DEBUG opcional

            boolean existe = UsuarioDAO.emailExiste(email);

            if (existe) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Email encontrado!");
                    new TelaAlterarSenha(email); // Passa o email para a próxima tela
                    dispose();
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Email não encontrado.");
                    dispose();
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
