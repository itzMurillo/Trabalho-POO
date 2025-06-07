package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Pais;
import java.sql.SQLException;
import java.util.List;

public interface PaisRepository {
    Pais insert(Pais pais) throws SQLException;
    Pais findById(Long id) throws SQLException;
    List<Pais> findAll() throws SQLException;
}