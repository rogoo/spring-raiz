package br.rosa.redis_ex.vo;

import java.io.Serializable;
import java.time.LocalDate;

public class Produto implements Serializable {
    private Integer id;
    private String nome;
    private LocalDate dataCadastro;

    public Produto() {
    }

    public Produto(Integer id, String nome, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
