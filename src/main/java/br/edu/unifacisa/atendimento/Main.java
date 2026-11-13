package br.edu.unifacisa.atendimento;

import br.edu.unifacisa.atendimento.db.ConexaoBD;
import br.edu.unifacisa.atendimento.model.Consulta;
import br.edu.unifacisa.atendimento.model.Medico;
import br.edu.unifacisa.atendimento.model.Paciente;
import br.edu.unifacisa.atendimento.model.Perfil;
import br.edu.unifacisa.atendimento.model.Secretaria;
import br.edu.unifacisa.atendimento.model.StatusConsulta;
import br.edu.unifacisa.atendimento.model.Usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema de Atendimento Médico — 1ª entrega");
        try (Connection conexao = ConexaoBD.getConnection()) {
            System.out.println("Conexão com o banco atendimento_medico estabelecida com sucesso.\n");
            listarUsuarios(conexao);
            listarConsultas(conexao);
        } catch (IOException | SQLException erro) {
            System.err.println("Não foi possível conectar ao banco de dados.");
            System.err.println("Confira o MySQL, os scripts do banco e o arquivo db.properties.");
        } catch (IllegalArgumentException erro) {
            System.err.println("Os dados encontrados no banco não correspondem ao modelo: " + erro.getMessage());
        }
    }

    private static void listarUsuarios(Connection conexao) throws SQLException {
        String sql = "SELECT id, nome, email, senha, perfil, crm, especialidade, cpf, telefone, criado_em "
                + "FROM usuarios ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                Perfil perfil = Perfil.deValorBanco(resultado.getString("perfil"));
                Timestamp criadoEm = resultado.getTimestamp("criado_em");
                Usuario usuario = switch (perfil) {
                    case MEDICO -> new Medico(resultado.getInt("id"), resultado.getString("nome"),
                            resultado.getString("email"), resultado.getString("senha"),
                            criadoEm == null ? null : criadoEm.toLocalDateTime(),
                            resultado.getString("crm"), resultado.getString("especialidade"));
                    case SECRETARIA -> new Secretaria(resultado.getInt("id"), resultado.getString("nome"),
                            resultado.getString("email"), resultado.getString("senha"),
                            criadoEm == null ? null : criadoEm.toLocalDateTime());
                    case PACIENTE -> new Paciente(resultado.getInt("id"), resultado.getString("nome"),
                            resultado.getString("email"), resultado.getString("senha"),
                            criadoEm == null ? null : criadoEm.toLocalDateTime(),
                            resultado.getString("cpf"), resultado.getString("telefone"));
                };
                usuarios.add(usuario);
            }
        }
        System.out.println("USUÁRIOS");
        usuarios.forEach(System.out::println);
        System.out.println();
    }

    private static void listarConsultas(Connection conexao) throws SQLException {
        String sql = "SELECT c.id, c.data_hora, c.status, c.observacoes, "
                + "p.id AS paciente_id, p.nome AS paciente_nome, p.email AS paciente_email, "
                + "p.senha AS paciente_senha, p.criado_em AS paciente_criado_em, p.cpf, p.telefone, "
                + "m.id AS medico_id, m.nome AS medico_nome, m.email AS medico_email, "
                + "m.senha AS medico_senha, m.criado_em AS medico_criado_em, m.crm, m.especialidade "
                + "FROM consultas c "
                + "JOIN usuarios p ON p.id = c.paciente_id AND p.perfil = ? "
                + "JOIN usuarios m ON m.id = c.medico_id AND m.perfil = ? "
                + "ORDER BY c.data_hora";
        List<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, Perfil.PACIENTE.getValorBanco());
            comando.setString(2, Perfil.MEDICO.getValorBanco());
            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    Timestamp pacienteCriadoEm = resultado.getTimestamp("paciente_criado_em");
                    Timestamp medicoCriadoEm = resultado.getTimestamp("medico_criado_em");
                    Paciente paciente = new Paciente(resultado.getInt("paciente_id"),
                            resultado.getString("paciente_nome"), resultado.getString("paciente_email"),
                            resultado.getString("paciente_senha"),
                            pacienteCriadoEm == null ? null : pacienteCriadoEm.toLocalDateTime(),
                            resultado.getString("cpf"), resultado.getString("telefone"));
                    Medico medico = new Medico(resultado.getInt("medico_id"),
                            resultado.getString("medico_nome"), resultado.getString("medico_email"),
                            resultado.getString("medico_senha"),
                            medicoCriadoEm == null ? null : medicoCriadoEm.toLocalDateTime(),
                            resultado.getString("crm"), resultado.getString("especialidade"));
                    Timestamp dataHora = resultado.getTimestamp("data_hora");
                    consultas.add(new Consulta(resultado.getInt("id"), paciente, medico,
                            dataHora.toLocalDateTime(), StatusConsulta.deValorBanco(resultado.getString("status")),
                            resultado.getString("observacoes")));
                }
            }
        }
        System.out.println("CONSULTAS");
        consultas.forEach(System.out::println);
    }
}
