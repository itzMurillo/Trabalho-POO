package br.unipar.easy.on.services;

import br.unipar.easy.on.domain.Cidade;
import br.unipar.easy.on.repositories.CidadeRepository;
import br.unipar.easy.on.repositories.CidadeRepositoryImp;

import java.sql.SQLException;
import java.util.List;

public class CidadeService {

    // interface pra manipulação de dados da entidade Cidade
    private CidadeRepository cidadeRepository;

    // construtor que inicializa o repositorio de Cidade
    public CidadeService() {
        this.cidadeRepository = new CidadeRepositoryImp();
    }

    public Cidade cadastrar(Cidade cidade) throws SQLException {
        return cidadeRepository.insert(cidade);
    }

    public Cidade buscarPorId(Long id) throws SQLException {
        return cidadeRepository.findById(id);
    }

    public List<Cidade> listarTodos() throws SQLException {
        return cidadeRepository.findAll();
    }
}
