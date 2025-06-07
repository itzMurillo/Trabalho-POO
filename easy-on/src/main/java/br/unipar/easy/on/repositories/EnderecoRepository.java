package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Endereco;
import java.sql.SQLException;
import java.util.List;

public interface EnderecoRepository {
    Endereco insert(Endereco endereco) throws SQLException;
    Endereco findById(Long id) throws SQLException;
    List<Endereco> findAll() throws SQLException;
}