package com.example.projetofinalpdm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.example.projetofinalpdm2.adpters.AdapterAulasDashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity3Dashboard extends Activity {
    private Singleton singleton = Singleton.getInstance();
    private TextView nome, profissao;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseAulas;
    RecyclerView recyclerView;
    List<Aula> aulaList;
    FloatingActionButton button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_dashboard);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseAulas = mDatabase.child("Aulas");
        mDatabaseAulas.addValueEventListener(criaEventoListenerAula());
        button = findViewById(R.id.botaoAdicionar);
        nome = findViewById(R.id.nomeUsuarioDashboard);
        profissao = findViewById(R.id.profissaoUsuarioDashboard);
        nome.setText(singleton.getUsuarioLogado().getNome());
        profissao.setText(singleton.getUsuarioLogado().getProfissao());
        recyclerView = findViewById(R.id.listaDisciplinasDisponiveis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        controlaBotao();
    }



    private void atualizaRecicleView() {
        RecyclerView.Adapter adapter = new AdapterAulasDashboard(aulaList, Activity3Dashboard.this);
        recyclerView.setAdapter(adapter);
    }

    public ValueEventListener criaEventoListenerAula(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Aula> aulas= new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Aula aula = d.getValue(Aula.class);
                    aulas.add(aula);
                }
                aulaList=aulas;
                atualizaRecicleView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("algum erro ocorreu");
            }
        };
        return postListener;
    }

    public void cadastrarAula(View view) {
        Intent intent = new Intent(Activity3Dashboard.this, Activity3Professor.class);
        startActivity(intent);
    }

    public void controlaBotao(){
        if (singleton.getOcupacaoUsuarioLogado().equals("Aluno"))
            button.setVisibility(View.INVISIBLE);
    }
}
