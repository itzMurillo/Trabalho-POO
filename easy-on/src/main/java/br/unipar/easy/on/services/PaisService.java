package br.unipar.easy.on.services;

import br.unipar.easy.on.domain.Pais;
import br.unipar.easy.on.repositories.PaisRepository;
import br.unipar.easy.on.repositories.PaisRepositoryImp;

import java.sql.SQLException;
import java.util.List;

public class PaisService {

    // interface do repositorio pra abstrair a implementação
    private PaisRepository paisRepository;

    // construtor que instancia a implementação concreta do repositorio
    public PaisService() {
        this.paisRepository = new PaisRepositoryImp();
    }

    public Pais cadastrar(Pais pais) throws SQLException {
        return paisRepository.insert(pais);
    }

    public Pais buscarPorId(Long id) throws SQLException {
        return paisRepository.findById(id);
    }

    public List<Pais> listarTodos() throws SQLException {
        return paisRepository.findAll();
    }
}
