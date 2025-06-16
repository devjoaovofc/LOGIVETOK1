package Tela.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	
    private static final String URL = "jdbc:mysql://localhost:3306/logivet"; // Corrigido
    private static final String USER = "root"; // Usuário do banco
    private static final String PASSWORD = "12345"; // Senha

    public static Connection conectar() throws SQLException {
    	
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

	public static Connection getConexao() {
		// TODO Auto-generated method stub
		return null;
	}
}
