package br.unipar.easy.on.repositories;

import br.unipar.easy.on.domain.Aluno;
import java.sql.SQLException;
import java.util.List;

// interface que define os metodos de acesso ao banco de dados pra entidade Aluno
public interface AlunoRepository {
    
    Aluno insert(Aluno aluno) throws SQLException; // insere um novo aluno no banco
    List<Aluno> findAll() throws SQLException; // retorna a lista de todos os alunos cadastrados
    Aluno update(Aluno aluno) throws SQLException; // atualiza os dados de um aluno existente
    void delete(Long id) throws SQLException; // remove um aluno pelo id
    Aluno findById(Long id) throws SQLException; // busca um aluno específico pelo id
    
}