package com.example.projetofinalpdm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.adpters.AdapterAulasDashboard;

import java.util.ArrayList;
import java.util.List;

public class VisualizaMinhaAula extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_minha_aula);
        ArrayList<Aula> aulas = (ArrayList<Aula>) getIntent().getSerializableExtra("List");
        RecyclerView recyclerView = findViewById(R.id.minhasAulas);
        RecyclerView.Adapter adapterAulasDashboard= new AdapterAulasDashboard(aulas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterAulasDashboard);
    }

    public void voltar(View view) {
        super.onBackPressed();
    }
}