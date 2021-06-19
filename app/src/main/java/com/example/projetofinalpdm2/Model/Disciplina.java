package com.example.projetofinalpdm2.Model;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private String nome;
    private Nivel nivel;

    public Disciplina() {
    }

    public Disciplina(String nome, Nivel nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

        public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return nome +
                ", " + nivel;
    }
}
