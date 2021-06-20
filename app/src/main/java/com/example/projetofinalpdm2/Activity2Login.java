package com.example.projetofinalpdm2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Activity2Login extends Activity {
    public static final String CHANEL_ID  = "100001";



    EditText user, password;
    String ocupacao;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseAulas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_login);
        ocupacao = getIntent().getStringExtra("ocupacao");
        user = findViewById(R.id.campoEmailLogin);
        password = findViewById(R.id.campoSenhaLogin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseAulas = mDatabase.child("Aulas");
    }

    public void logar(View view) {
        String s = user.getText().toString().replaceAll("\\.","");
        if (ocupacao.equals("Professor"))
            mDatabase.child("UsersP").child(s).get().addOnSuccessListener(
                    new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    if (usuario != null){
                        if (usuario.getSenha().equals(password.getText().toString())){
                            Singleton.getInstance().setUsuarioLogado(usuario);
                            Singleton.getInstance().setOcupacaoUsuarioLogado(ocupacao);
                            Intent intent = new Intent(Activity2Login.this, Activity3Dashboard.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Activity2Login.this, "Usuario ou Senha errado", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(Activity2Login.this, "Usuario ou Senha errado", Toast.LENGTH_SHORT).show();                }
            });
        else
            mDatabase.child("UsersA").child(s).get().addOnSuccessListener(
                    new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Usuario usuario = dataSnapshot.getValue(Usuario.class);
                            if (usuario != null) {
                                if (usuario.getSenha().equals(password.getText().toString())) {
                                    Singleton.getInstance().setUsuarioLogado(usuario);
                                    Singleton.getInstance().setOcupacaoUsuarioLogado(ocupacao);
                                    Intent intent = new Intent(Activity2Login.this, Activity3Dashboard.class);
                                    criarCanal();
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(Activity2Login.this, "Usuario ou Senha errado", Toast.LENGTH_SHORT).show();                                }
                            }else
                                Toast.makeText(Activity2Login.this, "Usuario ou Senha errado", Toast.LENGTH_SHORT).show();                        }
                    });
    }

    private void criarCanal(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence nome = "CanalNotificações";
            String descricao = "Canal de notificação de Aula para alunos AULÃOIFTO";
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canal = new NotificationChannel(CHANEL_ID, nome, importancia);
            canal.setDescription(descricao);
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(canal);
        }
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(Activity2Login.this, Activity2.class);
        intent.putExtra("ocupacao",ocupacao);
        startActivity(intent);
    }
}
