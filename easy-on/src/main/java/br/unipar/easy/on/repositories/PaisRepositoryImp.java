package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Pais;
import br.unipar.easy.on.infraestructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaisRepositoryImp implements PaisRepository {

    // comando SQL para inserir um novo país na tabela
    private static final String INSERT =
        "INSERT INTO pais (nome, sigla, aluno_cadastro, data_hora_cadastro) VALUES (?, ?, ?, ?)";

    // busca um país pelo ID
    private static final String FIND_BY_ID = "SELECT * FROM pais WHERE id = ?";

    // lista todos os países cadastrados
    private static final String FIND_ALL = "SELECT * FROM pais";

    @Override
    public Pais insert(Pais pais) throws SQLException {
        
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // preenche os parametros do INSERT
            ps.setString(1, pais.getNome());
            ps.setString(2, pais.getSigla());
            ps.setLong(3, pais.getAlunoCadastro());
            ps.setTimestamp(4, Timestamp.valueOf(pais.getDataHoraCadastro())); // converte LocalDateTime para Timestamp

            ps.executeUpdate(); // executa o comando no banco de dados

            // recupera o ID gerado automaticamente (é um autoincremento)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                pais.setId(rs.getLong(1)); // atualiza o objeto com o id gerado
            }
        }
        return pais; // retorna o objeto atualizado com id
    }

    @Override
    public Pais findById(Long id) throws SQLException {
        Pais pais = null;

        // aria conexão e prepara a consulta pra buscar país por id
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)) {

            ps.setLong(1, id); // define o id no parametro da consulta

            ResultSet rs = ps.executeQuery(); // executa a query

            if (rs.next()) {
                pais = new Pais(); // instancia um novo objeto País
                pais.setId(rs.getLong("id"));
                pais.setNome(rs.getString("nome"));
                pais.setSigla(rs.getString("sigla"));
                pais.setAlunoCadastro(rs.getLong("aluno_cadastro"));
                pais.setDataHoraCadastro(rs.getTimestamp("data_hora_cadastro").toLocalDateTime()); // converte Timestamp para LocalDateTime
            }
        }
        return pais; // eetorna o país encontrado ou aparece null
    }

    @Override
    public List<Pais> findAll() throws SQLException {
        List<Pais> paises = new ArrayList<>();

        // cria conexão, prepara a query de busca de todos os países e executa
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // pra cada linha do resultado, cria um novo objeto País e preenche os campos
                Pais pais = new Pais();
                pais.setId(rs.getLong("id"));
                pais.setNome(rs.getString("nome"));
                pais.setSigla(rs.getString("sigla"));
                pais.setAlunoCadastro(rs.getLong("aluno_cadastro"));
                pais.setDataHoraCadastro(rs.getTimestamp("data_hora_cadastro").toLocalDateTime());

                paises.add(pais); // adiciona o país à lista
            }
        }
        return paises; // e por ultimo, retorna a lista de todos os países
    }
}