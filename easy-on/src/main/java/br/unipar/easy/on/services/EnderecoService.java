package br.unipar.easy.on.services;

import br.unipar.easy.on.domain.Endereco;
import br.unipar.easy.on.exceptions.BusinessException;
import br.unipar.easy.on.repositories.EnderecoRepositoryImp;

import java.sql.SQLException;
import java.util.List;

public class EnderecoService {

    // repositorio responsável pelas operações CRUD de Endereco no banco
    private EnderecoRepositoryImp enderecoRepository;

    // construtor que inicializa o repositorio
    public EnderecoService() {
        this.enderecoRepository = new EnderecoRepositoryImp();
    }

    public Endereco insert(Endereco endereco) throws BusinessException, SQLException {
        if (endereco == null)
            throw new BusinessException("Informe os dados do endereço");

        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty())
            throw new BusinessException("Informe o logradouro");

        if (endereco.getCep() == null || endereco.getCep().trim().isEmpty())
            throw new BusinessException("Informe o CEP");

        return enderecoRepository.insert(endereco);
    }

    public Endereco update(Endereco endereco) throws BusinessException, SQLException {
        if (endereco == null || endereco.getId() == null)
            throw new BusinessException("Endereço inválido");

        // verifica se o endereço existe antes de atualizar
        Endereco existente = enderecoRepository.findById(endereco.getId());
        if (existente == null)
            throw new BusinessException("Endereço não encontrado");

        return endereco;
    }

    public Endereco findById(Long id) throws SQLException {
        return enderecoRepository.findById(id);
    }

    public List<Endereco> findAll() throws SQLException {
        return enderecoRepository.findAll();
    }
}
