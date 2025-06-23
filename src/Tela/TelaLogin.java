package Tela; // Define o pacote onde essa classe está, chamado "Tela"

import javax.swing.*; // Importa todos os componentes da biblioteca Swing (como JFrame, JButton, JLabel etc.)
import java.awt.*; // Importa classes gráficas do AWT (como Color, Graphics, Font etc.)
import java.awt.event.MouseAdapter; // Importa classe que permite tratar eventos de mouse de forma simplificada
import java.awt.event.MouseEvent; // Importa classe que representa um evento de clique do mouse
import java.awt.event.FocusAdapter; // Importa classe que facilita lidar com eventos de foco (campo selecionado)
import java.awt.event.FocusEvent; // Importa evento de foco (quando o campo ganha ou perde o foco)
import Tela.TelaMenuItens; // Importa outra tela do sistema, que será exibida após o login
import Tela.dao.UsuarioDAO; // Importa a classe de acesso ao banco para autenticar usuário
import Tela.model.Usuario; // Importa o modelo do usuário (provavelmente com atributos como nome, email, senha etc.)

public class TelaLogin extends JFrame { // Declara a classe TelaLogin que herda de JFrame (janela)
    private JTextField campoEmail; // Declara um campo de texto para o e-mail
    private JPasswordField campoSenha; // Declara um campo de senha (com ocultação dos caracteres)

    public TelaLogin() { // Construtor da tela de login
        super("LogiVet - Login"); // Define o título da janela

        setSize(1200, 700); // Define o tamanho da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Define que ao fechar a janela o app termina
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(null); // Define o layout como absoluto (x, y manual)

        JPanel painelEsquerdo = new JPanel(); // Cria um painel do lado esquerdo
        painelEsquerdo.setBounds(0, 0, 600, 700); // Define a posição e tamanho (x=0, y=0, largura=600, altura=700)
        painelEsquerdo.setBackground(Color.WHITE); // Define o fundo como branco
        painelEsquerdo.setLayout(null); // Layout absoluto também
        add(painelEsquerdo); // Adiciona o painel à janela principal

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png")); // Carrega a imagem da logo
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH); // Redimensiona a imagem
        JLabel logo = new JLabel(new ImageIcon(imagemRedimensionada)); // Cria um JLabel com a imagem
        logo.setBounds(150, 120, 300, 80); // Define a posição da logo no painel
        painelEsquerdo.add(logo); // Adiciona a logo ao painel esquerdo

        int baseY = 250; // Valor base para posicionar os campos verticais abaixo da logo

        JPanel painelEmail = new JPanel(null) { // Painel arredondado para o campo de e-mail
            protected void paintComponent(Graphics g) { // Sobrescreve a pintura do componente
                Graphics2D g2 = (Graphics2D) g; // Converte para Graphics2D
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Ativa suavização
                g2.setColor(getBackground()); // Usa a cor de fundo definida
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Desenha retângulo arredondado
            }
        };
        painelEmail.setBounds(150, 245, 300, 35); // Define posição e tamanho do painel de e-mail
        painelEmail.setOpaque(false); // Define como transparente
        painelEmail.setBackground(new Color(0xA6D5C1)); // Cor de fundo em verde claro
        painelEsquerdo.add(painelEmail); // Adiciona o painel ao painel esquerdo

        JLabel iconEmail = new JLabel(new ImageIcon(getClass().getResource("/Imagens/gmail 1.png"))); // Ícone do Gmail
        iconEmail.setBounds(5, 5, 24, 24); // Posição dentro do painel
        painelEmail.add(iconEmail); // Adiciona o ícone ao painel

        campoEmail = new JTextField("Digite seu email"); // Cria o campo com texto padrão
        campoEmail.setForeground(Color.GRAY); // Cor do texto em cinza (placeholder)
        campoEmail.setBounds(35, 5, 260, 24); // Posição dentro do painel
        campoEmail.setBorder(null); // Remove a borda padrão
        campoEmail.setOpaque(false); // Fundo transparente
        painelEmail.add(campoEmail); // Adiciona o campo ao painel

        campoEmail.addFocusListener(new FocusAdapter() { // Evento para quando o campo ganha ou perde foco
            public void focusGained(FocusEvent e) { // Ao clicar no campo
                if (campoEmail.getText().equals("Digite seu email")) { // Se for o texto padrão
                    campoEmail.setText(""); // Limpa o campo
                    campoEmail.setForeground(Color.BLACK); // Muda a cor para preto
                }
            }

            public void focusLost(FocusEvent e) { // Ao sair do campo
                if (campoEmail.getText().isEmpty()) { // Se estiver vazio
                    campoEmail.setForeground(Color.GRAY); // Volta a cor cinza
                    campoEmail.setText("Digite seu email"); // Volta o placeholder
                }
            }
        });

        JPanel painelSenha = new JPanel(null) { // Painel arredondado para senha
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painelSenha.setBounds(150, 300, 300, 35); // Define posição e tamanho
        painelSenha.setOpaque(false);
        painelSenha.setBackground(new Color(0xA6D5C1));
        painelEsquerdo.add(painelSenha);

        JLabel iconSenha = new JLabel(new ImageIcon(getClass().getResource("/Imagens/icone_senha.png"))); // Ícone de senha
        iconSenha.setBounds(5, 5, 24, 24);
        painelSenha.add(iconSenha);

        campoSenha = new JPasswordField("Digite sua senha"); // Campo de senha com texto padrão
        campoSenha.setForeground(Color.GRAY);
        campoSenha.setEchoChar((char) 0); // Mostra o texto ao invés de ●
        campoSenha.setBounds(35, 5, 260, 24);
        campoSenha.setBorder(null);
        campoSenha.setOpaque(false);
        painelSenha.add(campoSenha);

        campoSenha.addFocusListener(new FocusAdapter() { // Evento de foco
            public void focusGained(FocusEvent e) {
                String senha = new String(campoSenha.getPassword());
                if (senha.equals("Digite sua senha")) {
                    campoSenha.setText(""); // Limpa
                    campoSenha.setForeground(Color.BLACK);
                    campoSenha.setEchoChar('•'); // Volta a ocultar os caracteres
                }
            }

            public void focusLost(FocusEvent e) {
                String senha = new String(campoSenha.getPassword());
                if (senha.isEmpty()) {
                    campoSenha.setForeground(Color.GRAY);
                    campoSenha.setText("Digite sua senha");
                    campoSenha.setEchoChar((char) 0); // Mostra texto normal
                }
            }
        });

        JButton botaoLogin = new JButton("LOGIN") { // Cria botão com estilo personalizado
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        botaoLogin.setContentAreaFilled(false); // Remove preenchimento padrão
        botaoLogin.setFocusPainted(false); // Remove a borda ao focar
        botaoLogin.setOpaque(true); // Ativa opacidade
        botaoLogin.setBounds(150, baseY + 100, 300, 35); // Posição
        botaoLogin.setBackground(new Color(255, 127, 80)); // Laranja
        botaoLogin.setForeground(Color.WHITE); // Texto branco
        painelEsquerdo.add(botaoLogin); // Adiciona o botão

        botaoLogin.addActionListener(e -> { // Evento de clique no botão LOGIN
            UsuarioDAO usuarioDAO = new UsuarioDAO(); // Cria DAO para acessar banco
            String email = campoEmail.getText().trim(); // Pega email digitado
            String senha = new String(campoSenha.getPassword()); // Pega senha digitada

            if (email.equals("Digite seu email")) email = ""; // Limpa placeholder
            if (senha.equals("Digite sua senha")) senha = "";

            Usuario usuarioLogado = usuarioDAO.buscarUsuarioPorEmailESenha(email, senha); // Busca no banco

            if (usuarioLogado != null) { // Se encontrar
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!"); // Mostra sucesso
                new TelaMenuItens(usuarioLogado).setVisible(true); // Abre nova tela
                dispose(); // Fecha a tela atual
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE); // Erro
            }
        });

        JLabel cadastrar = new JLabel("Cadastre-se"); // Texto clicável para cadastro
        cadastrar.setFont(new Font("Arial", Font.PLAIN, 12));
        cadastrar.setBounds(150, baseY + 150, 150, 20);
        cadastrar.setForeground(Color.BLUE);
        painelEsquerdo.add(cadastrar);
        cadastrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaCadastro(); // Abre tela de cadastro
                dispose(); // Fecha login
            }
        });

        JLabel esqueceuSenha = new JLabel("Esqueceu a senha?"); // Texto clicável para recuperação
        esqueceuSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        esqueceuSenha.setForeground(Color.BLUE);
        esqueceuSenha.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muda cursor
        esqueceuSenha.setBounds(375, baseY + 150, 150, 20);
        painelEsquerdo.add(esqueceuSenha);
        esqueceuSenha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaRecuperarSenha().setVisible(true); // Abre tela de recuperar senha
                dispose(); // Fecha login
            }
        });

        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png"))); // Logo Uniceplac
        logoUniceplac.setBounds(70, 550, 250, 60);
        painelEsquerdo.add(logoUniceplac);

        JPanel painelDireito = new JPanel(); // Painel da direita
        painelDireito.setBounds(600, 0, 600, 700);
        painelDireito.setLayout(null);
        add(painelDireito);

        JLabel imagemLateral = new JLabel(new ImageIcon(getClass().getResource("/Imagens/imagem_lateral.png"))); // Imagem lateral
        imagemLateral.setBounds(0, 0, 600, 700);
        painelDireito.add(imagemLateral);

        setVisible(true); // Mostra a janela
    }
}
