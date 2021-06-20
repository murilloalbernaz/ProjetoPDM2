package com.example.projetofinalpdm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.DiasSemana;
import com.example.projetofinalpdm2.Model.Disciplina;
import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity3Professor extends Activity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseDisciplinas;
    private List<Disciplina> disciplinaList;
    private Spinner disciplinas;
    private Usuario usuariologado = Singleton.getInstance().getUsuarioLogado();
    private CheckBox c1,c2,c3,c4,c5,c6,c7;
    private TimePicker timePicker;
    private Singleton singleton = Singleton.getInstance();
    private Aula aula;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_professor);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabaseDisciplinas = mDatabase.child("Disciplinas");
        mDatabaseDisciplinas.addValueEventListener(criaEventoListenerDisciplinas());
        disciplinas = findViewById(R.id.campoDisciplina);
        c1 = findViewById(R.id.segunda_feira);
        c2 = findViewById(R.id.terca_feira);
        c3 = findViewById(R.id.quarta_feira);
        c4 = findViewById(R.id.quinta_feira);
        c5 = findViewById(R.id.sexta_feira);
        c6 = findViewById(R.id.sabado);
        c7 = findViewById(R.id.domingo);
        timePicker = findViewById(R.id.spinnerHora);
        singleton.setAula(new Aula());
    }

    public ValueEventListener criaEventoListenerDisciplinas(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Disciplina> disciplinaList1 = new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Disciplina disciplina = d.getValue(Disciplina.class);
                    disciplinaList1.add(disciplina);
                }
                disciplinaList = disciplinaList1;
                ArrayAdapter<Disciplina> adapter = new ArrayAdapter<>(Activity3Professor.this, android.R.layout.simple_spinner_dropdown_item, disciplinaList);
                disciplinas.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("algum erro ocorreu");
            }
        };
        return postListener;
    }


    public void atualizarAulaSingleton() {
        Disciplina disciplina = (Disciplina) disciplinas.getSelectedItem();
        List<DiasSemana> diasSemanaList = pegarDiasSemana();
        aula = singleton.getAula();
        aula.setProfessor(usuariologado);
        aula.setDisciplina(disciplina);
        aula.setDiasSemanaList(diasSemanaList);
        aula.setDisponivel(true);
        int hour, minuto;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minuto = timePicker.getMinute();
        }else{
            hour = timePicker.getCurrentHour();
            minuto = timePicker.getCurrentMinute();
        }
        aula.setHora(String.valueOf(hour));
        aula.setMinuto(String.valueOf(minuto));
        singleton.setAula(aula);
    }

    public void cadastrar(View view){
        atualizarAulaSingleton();
        String id = Aula.idAula(aula);
        mDatabase.child("Aulas").child(id).setValue(aula);
    }

    private List<DiasSemana> pegarDiasSemana() {
        List<DiasSemana> diasSemanaList= new ArrayList<>();
        if (c1.isChecked())
            diasSemanaList.add(DiasSemana.SEGUNDA);
        if(c2.isChecked())
            diasSemanaList.add(DiasSemana.TERÇA);
        if(c3.isChecked())
            diasSemanaList.add(DiasSemana.QUARTA);
        if(c4.isChecked())
            diasSemanaList.add(DiasSemana.QUINTA);
        if ((c5.isChecked()))
            diasSemanaList.add(DiasSemana.SEXTA);
        if(c6.isChecked())
            diasSemanaList.add(DiasSemana.SABADO);
        if(c7.isChecked())
            diasSemanaList.add(DiasSemana.DOMINGO);
        return diasSemanaList;
    }

    public void abrirmaps(View view) {
        atualizarAulaSingleton();
        Intent intent = new Intent(Activity3Professor.this, MapsActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroDisciplina(View view) {
        Intent intent = new Intent(Activity3Professor.this, Activity4.class);
        startActivity(intent);
    }
}
