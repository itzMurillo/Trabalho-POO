package br.unipar.easy.on.domain;

import java.time.LocalDateTime;

public class Aluno {
    
    private Long id;
    private String nome;
    private String email;
    private Endereco endereco;
    private LocalDateTime dataHoraCadastro;

    public Aluno() {}

    public Aluno(Long id, String nome, String email, Endereco endereco, LocalDateTime dataHoraCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public Endereco getEndereco() { 
        return endereco; 
    }
    public void setEndereco(Endereco endereco) { 
        this.endereco = endereco; 
    }

    public LocalDateTime getDataHoraCadastro() { 
        return dataHoraCadastro; 
    }
    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) { 
        this.dataHoraCadastro = dataHoraCadastro; 
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", endereco=" + endereco +
                ", dataHoraCadastro=" + dataHoraCadastro +
                '}';
    }
}