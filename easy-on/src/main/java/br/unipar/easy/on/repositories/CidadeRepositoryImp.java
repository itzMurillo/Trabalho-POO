package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Cidade;
import br.unipar.easy.on.domain.Estado;
import br.unipar.easy.on.infraestructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// implementação do repositorio de Cidade
public class CidadeRepositoryImp implements CidadeRepository {

    // comando SQL pra inserir uma nova cidade
    private static final String INSERT =
        "INSERT INTO cidade (nome, estado_id, aluno_cadastro, data_hora_cadastro) VALUES (?, ?, ?, ?)";

    // buscar uma cidade pelo ID
    private static final String FIND_BY_ID = "SELECT * FROM cidade WHERE id = ?";

    // listar todas as cidades
    private static final String FIND_ALL = "SELECT * FROM cidade";

    // inserção de uma cidade no banco
    @Override
    public Cidade insert(Cidade cidade) throws SQLException {
        try (
            Connection conn = new ConnectionFactory().getConnection(); // Cria conexão
            PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS) // Prepara insert
        ) {
            // preenche os dados da cidade
            ps.setString(1, cidade.getNome());
            ps.setLong(2, cidade.getEstado().getId());
            ps.setLong(3, cidade.getAlunoCadastro());
            ps.setTimestamp(4, Timestamp.valueOf(cidade.getDataHoraCadastro()));

            // executa o insert
            ps.executeUpdate();

            // recupera o id gerado no banco
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cidade.setId(rs.getLong(1));
                }
            }
        }

        return cidade;
    }

    // busca cidade pelo id
    @Override
    public Cidade findById(Long id) throws SQLException {
        Cidade cidade = null;

        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setLong(1, id); // define o id no parâmetro

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cidade = new Cidade();
                    cidade.setId(rs.getLong("id"));
                    cidade.setNome(rs.getString("nome"));

                    // cria um Estado só com o ID (tem um carregamento mais leve)
                    Estado estado = new Estado();
                    estado.setId(rs.getLong("estado_id"));
                    cidade.setEstado(estado);

                    cidade.setAlunoCadastro(rs.getLong("aluno_cadastro"));
                    cidade.setDataHoraCadastro(rs.getTimestamp("data_hora_cadastro").toLocalDateTime());
                }
            }
        }

        return cidade;
    }

    // lista todas as cidades
    @Override
    public List<Cidade> findAll() throws SQLException {
        List<Cidade> cidades = new ArrayList<>();

        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Cidade cidade = new Cidade();
                cidade.setId(rs.getLong("id"));
                cidade.setNome(rs.getString("nome"));

                Estado estado = new Estado();
                estado.setId(rs.getLong("estado_id"));
                cidade.setEstado(estado);

                cidade.setAlunoCadastro(rs.getLong("aluno_cadastro"));
                cidade.setDataHoraCadastro(rs.getTimestamp("data_hora_cadastro").toLocalDateTime());

                cidades.add(cidade);
            }
        }

        return cidades;
    }
}