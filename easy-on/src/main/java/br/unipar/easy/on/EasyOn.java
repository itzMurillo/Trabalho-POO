package br.unipar.easy.on;

// Aluno: Murillo Gabriel Moresco | RA: 60000080 | 3° periodo
// Aluno: Luan Maciel Ferreira | RA: 60002462 | 3° periodo
// Aluno: Pedro Henrique Montipó Becker | RA: 60003244 | 2° periodo

import br.unipar.easy.on.domain.*;
import br.unipar.easy.on.exceptions.BusinessException;
import br.unipar.easy.on.services.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class EasyOn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // criando o Scanner

        // instanciar services pra manipular as entidades
        AlunoService alunoService = new AlunoService();
        EnderecoService enderecoService = new EnderecoService();
        PaisService paisService = new PaisService();
        EstadoService estadoService = new EstadoService();
        CidadeService cidadeService = new CidadeService();

        try {
            // pedir os dados do país pro usuario
            System.out.println("Informe os dados do país:");
            System.out.print("Nome do país: ");
            String nomePais = scanner.nextLine();
            System.out.print("Sigla do país: ");
            String siglaPais = scanner.nextLine();

            // cria objeto Pais e preenche com os dados do usuario
            Pais pais = new Pais();
            pais.setNome(nomePais);
            pais.setSigla(siglaPais);
            pais.setAlunoCadastro(1L); // aqui defini id do aluno que cadastrou agora
            pais.setDataHoraCadastro(LocalDateTime.now()); // coloca data/hora atual
            pais = paisService.cadastrar(pais); // salva o país no banco

            // pedir os dados do estado
            System.out.println("\nInforme os dados do estado:");
            System.out.print("Nome do estado: ");
            String nomeEstado = scanner.nextLine();
            System.out.print("Sigla do estado: ");
            String siglaEstado = scanner.nextLine();

            // cria objeto Estado associa o país e salva
            Estado estado = new Estado();
            estado.setNome(nomeEstado);
            estado.setSigla(siglaEstado);
            estado.setPais(pais); // estado pertence ao país criado ali emcima
            estado.setAlunoCadastro(1L);
            estado.setDataHoraCadastro(LocalDateTime.now());
            estado = estadoService.cadastrar(estado);

            // pedi os dados da cidade pro usuario
            System.out.println("\nInforme os dados da cidade:");
            System.out.print("Nome da cidade: ");
            String nomeCidade = scanner.nextLine();

            // cria objeto Cidade associa o estado e salva
            Cidade cidade = new Cidade();
            cidade.setNome(nomeCidade);
            cidade.setEstado(estado);
            cidade.setAlunoCadastro(1L);
            cidade.setDataHoraCadastro(LocalDateTime.now());
            cidade = cidadeService.cadastrar(cidade);

            // pedi os dados do endereço
            System.out.println("\nInforme os dados do endereço:");
            System.out.print("Logradouro: ");
            String logradouro = scanner.nextLine();
            System.out.print("Número: ");
            String numero = scanner.nextLine();
            System.out.print("Complemento: ");
            String complemento = scanner.nextLine();
            System.out.print("Bairro: ");
            String bairro = scanner.nextLine();
            System.out.print("CEP: ");
            String cep = scanner.nextLine();

            // cria objeto Endereco associa a cidade e salva
            Endereco endereco = new Endereco();
            endereco.setLogradouro(logradouro);
            endereco.setNumero(numero);
            endereco.setComplemento(complemento);
            endereco.setBairro(bairro);
            endereco.setCep(cep);
            endereco.setCidade(cidade);
            endereco = enderecoService.insert(endereco);

            // pedi os dados do aluno
            System.out.println("\nInforme os dados do aluno:");
            System.out.print("Nome: ");
            String nomeAluno = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            // cria objeto Aluno associa endereço e salva
            Aluno aluno = new Aluno();
            aluno.setNome(nomeAluno);
            aluno.setEmail(email);
            aluno.setEndereco(endereco);
            aluno.setDataHoraCadastro(LocalDateTime.now());
            aluno = alunoService.insert(aluno);

            // mostra mensagem de sucesso com o ID do aluno cadastrado
            System.out.println("\nAluno cadastrado com sucesso! ID: " + aluno.getId());

            // mostra lista de todos os alunos cadastrados
            System.out.println("\n--- Lista de Alunos ---");
            List<Aluno> alunos = alunoService.findAll();

            for (Aluno a : alunos) {
                System.out.println("ID: " + a.getId());
                System.out.println("Nome: " + a.getNome());
                System.out.println("Email: " + a.getEmail());
                if (a.getEndereco() != null) {
                    System.out.println("Endereço: " + a.getEndereco());
                } else {
                    System.out.println("Endereço: Não informado");
                }
                System.out.println("Data Cadastro: " + a.getDataHoraCadastro());
                System.out.println("----------------------------");
            }

        } catch (BusinessException | SQLException e) {
            // caso ocorra algum erro, mostra a mensagem para o usuário
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close(); // aqui fecha o scanner
        }
    }
}