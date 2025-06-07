package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Estado;
import br.unipar.easy.on.domain.Pais;
import br.unipar.easy.on.infraestructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoRepositoryImp implements EstadoRepository {

    // Comando SQL pra inserir um estado no banco de dados
    private static final String INSERT =
        "INSERT INTO estado (nome, sigla, pais_id, aluno_cadastro, data_hora_cadastro) VALUES (?, ?, ?, ?, ?)";

    // buscar um estado pelo ID
    private static final String FIND_BY_ID = "SELECT * FROM estado WHERE id = ?";

    // buscar todos os estados
    private static final String FIND_ALL = "SELECT * FROM estado";

    @Override
    public Estado insert(Estado estado) throws SQLException {
        
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // preenchendo os parâmetros do INSERT
            ps.setString(1, estado.getNome());
            ps.setString(2, estado.getSigla());
            ps.setLong(3, estado.getPais().getId()); // assumi que o objeto Pais já está preenchido
            ps.setLong(4, estado.getAlunoCadastro());
            ps.setTimestamp(5, Timestamp.valueOf(estado.getDataHoraCadastro())); // converte LocalDateTime para Timestamp

            ps.executeUpdate(); // executa o INSERT

            // recupera o ID gerado automaticamente pelo banco
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    estado.setId(rs.getLong(1)); // atribui o ID gerado ao objeto
                }
            }
        }
        return estado; // retorna o objeto já com o id preenchido
    }

    @Override
    public Estado findById(Long id) throws SQLException {
        Estado estado = null;

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)) {

            ps.setLong(1, id); // defini o ID como parametro

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    estado = new Estado(); // instancia objeto Estado
                    estado.setId(rs.getLong("id"));
                    estado.setNome(rs.getString("nome"));
                    estado.setSigla(rs.getString("sigla"));

                    // cria objeto Pais apenas com o ID (sem JOIN)
                    Pais pais = new Pais();
                    pais.setId(rs.getLong("pais_id"));
                    estado.setPais(pais);

                    estado.setAlunoCadastro(rs.getLong("aluno_cadastro"));
                    estado.setDataHoraCadastro(rs.getTimestamp("data_hora_cadastro").toLocalDateTime());
                }
            }
        }

        return estado; // vai aparecer null se não encontrar
    }

    @Override
    public List<Estado> findAll() throws SQLException {
        List<Estado> estados = new ArrayList<>();

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estado estado = new Estado(); // cria um novo estado a cada linha
                estado.setId(rs.getLong("id"));
                estado.setNome(rs.getString("nome"));
                estado.setSigla(rs.getString("sigla"));

                // cria o objeto Pais apenas com o ID
                Pais pais = new Pais();
                pais.setId(rs.getLong("pais_id"));
                estado.setPais(pais);

                estado.setAlunoCadastro(rs.getLong("aluno_cadastro"));
                estado.setDataHoraCadastro(rs.getTimestamp("data_hora_cadastro").toLocalDateTime());

                estados.add(estado); // adiciona à lista
            }
        }

        return estados;
    }
}