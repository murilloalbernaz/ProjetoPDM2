package com.example.projetofinalpdm2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalpdm2.Model.Disciplina;
import com.example.projetofinalpdm2.Model.Nivel;
import com.example.projetofinalpdm2.adpters.AddpterCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity4 extends Activity {
    List<Disciplina> disciplinaList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseDisciplinas;
    private EditText nome;
    private Spinner spinner;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseDisciplinas = mDatabase.child("Disciplinas");
        mDatabaseDisciplinas.addValueEventListener(criaEventoListenerDisciplinas());
        spinner = findViewById(R.id.campoNivel);
        ArrayAdapter<Nivel> adapter = new ArrayAdapter<Nivel>(this, android.R.layout.simple_spinner_dropdown_item, Nivel.values());
        spinner.setAdapter(adapter);
        nome = findViewById(R.id.campoDisciplina);
        recyclerView = findViewById(R.id.listaSuasDisciplinas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView.Adapter adapterRecicle = new AddpterCard(disciplinaList);
        recyclerView.setAdapter(adapterRecicle);
    }

    public void criar(View view) {
        Disciplina disciplina = new Disciplina();
        String s = spinner.getSelectedItem().toString();
        if (s.equals("BASICO"))
            disciplina.setNivel(Nivel.BASICO);
        else if (s.equals("MEDIO"))
            disciplina.setNivel(Nivel.MEDIO);
        else if (s.equals("AVANCADO"))
            disciplina.setNivel(Nivel.AVANCADO);
        disciplina.setNome(nome.getText().toString().toUpperCase());
        mDatabase.child("Disciplinas").child(disciplina.getNome()+disciplina.getNivel().toString()).setValue(disciplina);
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
                RecyclerView.Adapter adapterRecicle = new AddpterCard(disciplinaList);
                recyclerView.setAdapter(adapterRecicle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("algum erro ocorreu");
            }
        };
        return postListener;
    }

    public void voltar(View view) {
        super.onBackPressed();
    }
}
