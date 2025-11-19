package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class
Conexao {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/gestao_qualidade?user=root";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
