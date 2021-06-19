package com.example.projetofinalpdm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity2Login extends Activity {
    EditText user, password;
    String ocupacao;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_login);
        ocupacao = getIntent().getStringExtra("ocupacao");
        user = findViewById(R.id.campoEmailLogin);
        password = findViewById(R.id.campoSenhaLogin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(Activity2Login.this, "Usuario ou Senha errado", Toast.LENGTH_SHORT).show();                                }
                            }else
                                Toast.makeText(Activity2Login.this, "Usuario ou Senha errado", Toast.LENGTH_SHORT).show();                        }
                    });
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(Activity2Login.this, Activity2.class);
        intent.putExtra("ocupacao",ocupacao);
        startActivity(intent);
    }
}
