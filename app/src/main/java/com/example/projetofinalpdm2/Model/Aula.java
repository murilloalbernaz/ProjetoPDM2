package com.example.projetofinalpdm2.Model;

import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aula implements Serializable {
    private Usuario professor;
    private List<Usuario> alunos;
    private Disciplina disciplina;
    private List<DiasSemana> diasSemanaList;
    private String hora;
    private String minuto;
    private boolean disponivel;
    private MyLatlang latLng;


    public Aula() {
    }

    public Aula(Usuario professor, Disciplina disciplina, List<DiasSemana> diasSemanaList, boolean disponivel) {
        this.professor = professor;
        this.disciplina = disciplina;
        this.diasSemanaList = diasSemanaList;
        this.disponivel =disponivel;
    }

    public List<Usuario> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Usuario> alunos) {
        this.alunos = alunos;
    }

    public MyLatlang getLatLng() { return latLng; }

    public void setLatLng(MyLatlang latLng) { this.latLng = latLng; }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<DiasSemana> getDiasSemanaList() {
        return diasSemanaList;
    }

    public void setDiasSemanaList(List<DiasSemana> diasSemanaList) {
        this.diasSemanaList = diasSemanaList;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public final static String idAula(Aula aula){
        return aula.getDisciplina().getNome()+aula.getDisciplina().getNivel().toString() + aula.getProfessor().getNome()+aula.getHora()+aula.getMinuto();
    };

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("professor", professor);
        result.put("alunos", alunos);
        result.put("Disciplina", disciplina);
        result.put("dias", diasSemanaList);
        result.put("hora", hora);
        result.put("minuto",minuto);
        result.put("disponivel", disponivel);
        result.put("Lat", latLng);
        return result;
    }
}
