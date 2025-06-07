package br.unipar.easy.on.domain;

import java.time.LocalDateTime;

public class Pais {

    private Long id;
    private String nome;
    private String sigla;
    private String codigo;
    private Long alunoCadastro;
    private LocalDateTime dataHoraCadastro;

    public Pais() {
    }

    public Pais(Long id, String nome, String sigla, String codigo, Long alunoCadastro, LocalDateTime dataHoraCadastro) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.codigo = codigo;
        this.alunoCadastro = alunoCadastro;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return "Pais{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + '}';
    }

}