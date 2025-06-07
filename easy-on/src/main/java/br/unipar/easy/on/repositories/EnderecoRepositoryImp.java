package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Endereco;
import br.unipar.easy.on.domain.Cidade;
import br.unipar.easy.on.domain.Estado;
import br.unipar.easy.on.domain.Pais;
import br.unipar.easy.on.infraestructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// implementação do repositório de Endereco
public class EnderecoRepositoryImp implements EnderecoRepository {

    // inseri endereço simples (sem JOIN)
    private static final String INSERT =
        "INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade_id) VALUES (?, ?, ?, ?, ?, ?)";

    // tive que deixa a consulta SQL assim pra busca endereço junto com cidade, estado e país, usando o comando JOIN
    // e pra renomear as colunas com "alias" pra não da conflito de nomes repetidos nas tabelas relacionadas
    private static final String FIND_BY_ID_JOIN =
        "SELECT e.*, " +
        "       c.id AS c_id, c.nome AS c_nome, " +
        "       est.id AS est_id, est.nome AS est_nome, est.sigla AS est_sigla, " +
        "       p.id AS p_id, p.nome AS p_nome, p.sigla AS p_sigla " +
        "FROM endereco e " +
        "JOIN cidade c ON e.cidade_id = c.id " +
        "JOIN estado est ON c.estado_id = est.id " +
        "JOIN pais p ON est.pais_id = p.id " +
        "WHERE e.id = ?";

    // mesma ideia, mas buscando todos os endereços do banco
    private static final String FIND_ALL_JOIN =
        "SELECT e.*, " +
        "       c.id AS c_id, c.nome AS c_nome, " +
        "       est.id AS est_id, est.nome AS est_nome, est.sigla AS est_sigla, " +
        "       p.id AS p_id, p.nome AS p_nome, p.sigla AS p_sigla " +
        "FROM endereco e " +
        "JOIN cidade c ON e.cidade_id = c.id " +
        "JOIN estado est ON c.estado_id = est.id " +
        "JOIN pais p ON est.pais_id = p.id";

    // insere um novo endereço no banco
    @Override
    public Endereco insert(Endereco endereco) throws SQLException {
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, endereco.getLogradouro());
            ps.setString(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCep());
            ps.setLong(6, endereco.getCidade().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setId(rs.getLong(1)); // aqui pega o id gerado no banco
                }
            }
        }
        return endereco;
    }

    // busca endereço completo (com cidade, estado e país) pelo id
    @Override
    public Endereco findById(Long id) throws SQLException {
        Endereco endereco = null;

        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_JOIN)
        ) {
            ps.setLong(1, id); // define o id no WHERE do banco
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    endereco = montarEnderecoCompleto(rs); // monta o objeto com base no resultSet
                }
            }
        }

        return endereco;
    }

    // lista todos os endereços completos (com JOIN)
    @Override
    public List<Endereco> findAll() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();

        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_ALL_JOIN);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Endereco endereco = montarEnderecoCompleto(rs);
                enderecos.add(endereco);
            }
        }

        return enderecos;
    }

    // metodo auxiliar, pra montar o objeto Endereco com todas as entidades relacionadas
    private Endereco montarEnderecoCompleto(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();

        endereco.setId(rs.getLong("id"));
        endereco.setLogradouro(rs.getString("logradouro"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCep(rs.getString("cep"));

        // monta Cidade
        Cidade cidade = new Cidade();
        cidade.setId(rs.getLong("c_id"));
        cidade.setNome(rs.getString("c_nome"));

        // monta Estado
        Estado estado = new Estado();
        estado.setId(rs.getLong("est_id"));
        estado.setNome(rs.getString("est_nome"));
        estado.setSigla(rs.getString("est_sigla"));

        // monta País
        Pais pais = new Pais();
        pais.setId(rs.getLong("p_id"));
        pais.setNome(rs.getString("p_nome"));
        pais.setSigla(rs.getString("p_sigla"));

        // liga tudo: país → estado → cidade → endereco
        estado.setPais(pais);
        cidade.setEstado(estado);
        endereco.setCidade(cidade);

        return endereco;
    }
}