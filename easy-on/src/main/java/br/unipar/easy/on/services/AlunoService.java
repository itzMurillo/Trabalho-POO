package br.unipar.easy.on.services;

import br.unipar.easy.on.domain.Aluno;
import br.unipar.easy.on.domain.Endereco;
import br.unipar.easy.on.exceptions.BusinessException;
import br.unipar.easy.on.repositories.AlunoRepositoryImp;

import java.sql.SQLException;
import java.util.List;

public class AlunoService {

    // repositorio pra operações CRUD no aluno
    private AlunoRepositoryImp alunoRepository;
    // serviço pra operações com endereço relacionado ao aluno
    private EnderecoService enderecoService;

    // construtor inicializa os repositorios e serviços necessários
    public AlunoService() {
        this.alunoRepository = new AlunoRepositoryImp();
        this.enderecoService = new EnderecoService();
    }

    public Aluno insert(Aluno aluno) throws BusinessException, SQLException {
        // valida o objeto aluno
        if (aluno == null)
            throw new BusinessException("Informe os dados do aluno");
        // valida o nome
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty())
            throw new BusinessException("Informe o nome do aluno");
        if (aluno.getNome().length() < 3)
            throw new BusinessException("O nome deve conter mais do que 3 caracteres.");
        if (aluno.getNome().length() > 60)
            throw new BusinessException("O nome não deve possuir mais do que 60 caracteres.");
        // valida o email
        if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty())
            throw new BusinessException("Informe o email do aluno");
        // valida o endereço
        if (aluno.getEndereco() == null)
            throw new BusinessException("Informe o endereço do aluno");

        // persisti o endereço antes de persistir o aluno, garantindo integridade
        Endereco enderecoPersistido = enderecoService.insert(aluno.getEndereco());
        aluno.setEndereco(enderecoPersistido);

        // inserir o aluno com endereço persistido e retornar objeto atualizado
        return alunoRepository.insert(aluno);
    }

    public List<Aluno> findAll() throws SQLException {
        return alunoRepository.findAll();
    }

    public Aluno findById(Integer id) throws SQLException {
        return alunoRepository.findById(id.longValue());
    }

    public Aluno update(Aluno aluno) throws BusinessException, SQLException {
        // validações básicas do objeto aluno e do id
        if (aluno == null || aluno.getId() == null)
            throw new BusinessException("Informe os dados do aluno");
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty())
            throw new BusinessException("Informe o nome do aluno");
        if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty())
            throw new BusinessException("Informe o email do aluno");
        if (aluno.getEndereco() == null)
            throw new BusinessException("Informe o endereço do aluno");
        // verifica se aluno existe no banco
        Aluno existe = alunoRepository.findById(aluno.getId());
        if (existe == null)
            throw new BusinessException("Aluno não encontrado");
        // atualiza endereço relacionado e seta no aluno
        Endereco enderecoAtualizado = enderecoService.update(aluno.getEndereco());
        aluno.setEndereco(enderecoAtualizado);
        // e por ultimo atualiza o aluno no banco e retorna o objeto atualizado
        return alunoRepository.update(aluno);
    }

    public void delete(Integer id) throws SQLException, BusinessException {
        // verifica se o aluno existe antes de remover
        Aluno existe = alunoRepository.findById(id.longValue());
        if (existe == null)
            throw new BusinessException("Aluno não encontrado");

        // remove o aluno pelo id
        alunoRepository.delete(id.longValue());
    }
}