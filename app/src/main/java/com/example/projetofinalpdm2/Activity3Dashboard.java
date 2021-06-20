package com.example.projetofinalpdm2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.Utils.MyBrodCastReciver;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.example.projetofinalpdm2.adpters.AdapterAulasDashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Activity3Dashboard extends Activity {
    private Singleton singleton = Singleton.getInstance();
    private TextView nome, profissao;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseAulas;
    RecyclerView recyclerView;
    List<Aula> aulaList, aulasSemfiltro;
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
                aulasSemfiltro = new ArrayList<>();
                List<Aula> aulas= new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Aula aula = d.getValue(Aula.class);
                    if (aula.isDisponivel() == true)
                        aulas.add(aula);
                    aulasSemfiltro.add(aula);
                }
                aulaList=aulas;
                atualizaRecicleView();
                if (singleton.getOcupacaoUsuarioLogado().equals("Aluno"))
                    criaAlarmManager();
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
            button.setVisibility(View.GONE);
    }

    public void criaAlarmManager(){
        if (singleton.isAlarmCriado() == false){
            AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext() , MyBrodCastReciver.class);
            ArrayList<Aula> aulasLogado = aulaFiltrada(aulaList);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lista1", aulasLogado);
            intent.putExtra("listaAula", bundle);
            Calendar calendar = Calendar.getInstance();
            Date currDate = calendar.getTime();
            currDate.setHours(21);
            currDate.setMinutes(24);
            calendar.setTime(currDate);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                System.out.println(calendar.getTime());
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            singleton.setAlarmCriado(true);
        }
    }

    private ArrayList<Aula> aulaFiltrada(List<Aula> aulas) {
        ArrayList<Aula> aulasFiltrada = new ArrayList<>();
        if (singleton.getOcupacaoUsuarioLogado().equals("Aluno")){
            for (Aula a: aulas){
                if (a.getAlunos() != null)
                    for (Usuario u: a.getAlunos()){
                        if(u.equals(singleton.getUsuarioLogado()))
                            aulasFiltrada.add(a);
                    }
            }
        }else{
            for (Aula a: aulas){
                if (a.getProfessor()!= null)
                        if(a.getProfessor().equals(singleton.getUsuarioLogado()))
                            aulasFiltrada.add(a);
            }
        }
        return aulasFiltrada;
    }

    public void visualizarMinhasAulas(View view) {
        Intent intent = new Intent(Activity3Dashboard.this, VisualizaMinhaAula.class);
        intent.putExtra("List", aulaFiltrada(aulasSemfiltro));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
