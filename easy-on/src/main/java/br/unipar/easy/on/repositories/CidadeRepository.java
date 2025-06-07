package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Cidade;

import java.sql.SQLException;
import java.util.List;

public interface CidadeRepository {
    Cidade insert(Cidade cidade) throws SQLException;
    Cidade findById(Long id) throws SQLException;
    List<Cidade> findAll() throws SQLException;
}