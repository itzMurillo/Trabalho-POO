package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Aluno;
import br.unipar.easy.on.domain.Endereco;
import br.unipar.easy.on.infraestructure.ConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// implementação da interface AlunoRepository
public class AlunoRepositoryImp implements AlunoRepository {

    // comando SQL pra inserir aluno
    private static final String INSERT =
            "INSERT INTO aluno (nome, email, endereco_id, datahora_cadastro) VALUES (?, ?, ?, ?)";

    // listar todos os alunos
    private static final String FIND_ALL =
            "SELECT id, nome, email, endereco_id, datahora_cadastro FROM aluno ORDER BY id ASC";

    // buscar um aluno pelo id
    private static final String FIND_BY_ID =
            "SELECT id, nome, email, endereco_id, datahora_cadastro FROM aluno WHERE id = ?";

    // atualizar os dados de um aluno
    private static final String UPDATE =
            "UPDATE aluno SET nome = ?, email = ?, endereco_id = ?, datahora_cadastro = ? WHERE id = ?";

    // deletar um aluno
    private static final String DELETE =
            "DELETE FROM aluno WHERE id = ?";

    // repositorio para buscar dados do endereço do aluno
    private EnderecoRepository enderecoRepository = new EnderecoRepositoryImp();

    @Override
    public Aluno insert(Aluno aluno) throws SQLException {
        // conecta no banco e prepara o insert
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            // preenche os valores do insert
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());

            // verifica se o aluno tem endereço
            if (aluno.getEndereco() != null && aluno.getEndereco().getId() != null) {
                ps.setLong(3, aluno.getEndereco().getId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }

            // define a data de cadastro
            if (aluno.getDataHoraCadastro() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(aluno.getDataHoraCadastro()));
            } else {
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }

            // executa o insert
            ps.executeUpdate();

            // recupera o id gerado automaticamente
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                aluno.setId(rs.getLong(1));
            }
        } finally {
            // fecha os recursos
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return aluno;
    }

    @Override
    public List<Aluno> findAll() throws SQLException {
        // lista para armazenar os alunos encontrados
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(FIND_ALL);
            rs = ps.executeQuery();

            // percorre o resultado e preenche a lista
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getLong("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));

                // busca o endereço se tiver id
                Long enderecoId = rs.getLong("endereco_id");
                if (!rs.wasNull()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    aluno.setEndereco(endereco);
                }

                // converte o timestamp para LocalDateTime
                Timestamp ts = rs.getTimestamp("datahora_cadastro");
                if (ts != null) {
                    aluno.setDataHoraCadastro(ts.toLocalDateTime());
                }

                alunos.add(aluno);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return alunos;
    }

    @Override
    public Aluno update(Aluno aluno) throws SQLException {
        // atualiza os dados do aluno no banco
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(UPDATE);

            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());

            if (aluno.getEndereco() != null && aluno.getEndereco().getId() != null) {
                ps.setLong(3, aluno.getEndereco().getId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }

            if (aluno.getDataHoraCadastro() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(aluno.getDataHoraCadastro()));
            } else {
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }

            ps.setLong(5, aluno.getId());

            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return aluno;
    }

    @Override
    public void delete(Long id) throws SQLException {
        // deleta o aluno pelo id
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(DELETE);
            ps.setLong(1, id);
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }

    @Override
    public Aluno findById(Long id) throws SQLException {
        // busca um aluno específico pelo id
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getLong("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));

                Long enderecoId = rs.getLong("endereco_id");
                if (!rs.wasNull()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    aluno.setEndereco(endereco);
                }

                Timestamp ts = rs.getTimestamp("datahora_cadastro");
                if (ts != null) {
                    aluno.setDataHoraCadastro(ts.toLocalDateTime());
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return aluno;
    }
}