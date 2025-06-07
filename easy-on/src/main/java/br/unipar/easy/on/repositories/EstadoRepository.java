package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Estado;

import java.sql.SQLException;
import java.util.List;

public interface EstadoRepository {
    Estado insert(Estado estado) throws SQLException;
    Estado findById(Long id) throws SQLException;
    List<Estado> findAll() throws SQLException;
}
