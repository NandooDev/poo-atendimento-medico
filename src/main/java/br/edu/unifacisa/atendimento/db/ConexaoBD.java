package br.edu.unifacisa.atendimento.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexaoBD {
    private static final String ARQUIVO_CONFIGURACAO = "/db.properties";

    private ConexaoBD() { }

    public static Connection getConnection() throws SQLException, IOException {
        Properties propriedades = new Properties();
        try (InputStream entrada = ConexaoBD.class.getResourceAsStream(ARQUIVO_CONFIGURACAO)) {
            if (entrada == null) {
                throw new IOException("Arquivo db.properties não encontrado. Copie db.properties.example e configure o acesso ao MySQL.");
            }
            propriedades.load(entrada);
        }

        String url = propriedades.getProperty("db.url");
        String usuario = propriedades.getProperty("db.usuario");
        String senha = propriedades.getProperty("db.senha");
        if (url == null || url.isBlank() || usuario == null || usuario.isBlank() || senha == null) {
            throw new IOException("Configure db.url, db.usuario e db.senha no arquivo db.properties.");
        }
        return DriverManager.getConnection(url, usuario, senha);
    }
}
