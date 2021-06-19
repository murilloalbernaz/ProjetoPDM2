package com.example.projetofinalpdm2.Utils;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.Usuario;

public class Singleton {
    private static Singleton  singleton;
    private Usuario usuarioLogado;
    private Aula aula;
    private String ocupacaoUsuarioLogado;

    private Singleton(){

    }

    public static synchronized Singleton getInstance() {
        if (singleton == null)
            singleton = new Singleton();

        return singleton;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public String getOcupacaoUsuarioLogado() {
        return ocupacaoUsuarioLogado;
    }

    public void setOcupacaoUsuarioLogado(String ocupacaoUsuarioLogado) {
        this.ocupacaoUsuarioLogado = ocupacaoUsuarioLogado;
    }
}
