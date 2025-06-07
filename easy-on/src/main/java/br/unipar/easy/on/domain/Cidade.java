package br.unipar.easy.on.domain;

import java.time.LocalDateTime;

public class Cidade {
    
    private Long id;
    private String codigoIbge;
    private String nome;
    private Estado estado;
    private Long alunoCadastro;
    private LocalDateTime dataHoraCadastro;

    public Cidade() {}

    public Cidade(Long id, String codigoIbge, String nome, Estado estado, Long alunoCadastro, LocalDateTime dataHoraCadastro) {
        this.id = id;
        this.codigoIbge = codigoIbge;
        this.nome = nome;
        this.estado = estado;
        this.alunoCadastro = alunoCadastro;
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getAlunoCadastro() {
        return alunoCadastro;
    }

    public void setAlunoCadastro(Long alunoCadastro) {
        this.alunoCadastro = alunoCadastro;
    }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    @Override
    public String toString() {
        return "Cidade{" + "id=" + id + ", nome=" + nome + ", estado=" + estado + '}';
    }

}