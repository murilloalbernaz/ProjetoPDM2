package com.example.projetofinalpdm2.Model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nome;
    private String email;
    private String senha;
    private String profissao;
    private String instituicao;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String profissao, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.profissao = profissao;
        this.instituicao = instituicao;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
}
