package br.com.cinema.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/cinema";

    private static final String USER =
            "postgres";

    private static final String PASSWORD =
            "123456";

    public static Connection conectar() throws Exception {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}