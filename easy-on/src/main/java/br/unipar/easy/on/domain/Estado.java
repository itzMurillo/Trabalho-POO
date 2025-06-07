package br.unipar.easy.on.domain;

import java.time.LocalDateTime;

public class Estado {
    
    private Long id;
    private String nome;
    private String codigoIbge;
    private String sigla;
    private Pais pais;
    private Long alunoCadastro;
    private LocalDateTime dataHoraCadastro;

    public Estado() {}

    public Estado(Long id, String nome, String codigoIbge, String sigla, Pais pais, Long alunoCadastro, LocalDateTime dataHoraCadastro) {
        this.id = id;
        this.nome = nome;
        this.codigoIbge = codigoIbge;
        this.sigla = sigla;
        this.pais = pais;
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

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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
        return "Estado{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", pais=" + pais + '}';
    }
    
}