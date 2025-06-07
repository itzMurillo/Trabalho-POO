package br.unipar.easy.on.services;

import br.unipar.easy.on.domain.Estado;
import br.unipar.easy.on.repositories.EstadoRepository;
import br.unipar.easy.on.repositories.EstadoRepositoryImp;

import java.sql.SQLException;
import java.util.List;

public class EstadoService {

    // interface do repositorio pra abstrair implementação
    private EstadoRepository estadoRepository;

    // construtor que instancia a implementação concreta do repositorio
    public EstadoService() {
        this.estadoRepository = new EstadoRepositoryImp();
    }

    public Estado cadastrar(Estado estado) throws SQLException {
        return estadoRepository.insert(estado);
    }

    public Estado buscarPorId(Long id) throws SQLException {
        return estadoRepository.findById(id);
    }

    public List<Estado> listarTodos() throws SQLException {
        return estadoRepository.findAll();
    }
}