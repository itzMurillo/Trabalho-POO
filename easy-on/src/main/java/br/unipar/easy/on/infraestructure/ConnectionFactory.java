package br.unipar.easy.on.infraestructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// aqui é pra conectar no banco de dados do PgAdmin
public class ConnectionFactory {
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/easy_on", 
                "postgres",
                ""
        );
    }
}