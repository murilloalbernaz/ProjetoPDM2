package com.example.projetofinalpdm2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinalpdm2.Model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity2 extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String ocupacao;
    EditText nome, email, senha, profissao, instituicao;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ocupacao = getIntent().getStringExtra("ocupacao");
        nome = findViewById(R.id.campoNome);
        email = findViewById(R.id.campoEmail);
        senha = findViewById(R.id.campoSenha);
        profissao = findViewById(R.id.campoProfissao);
        instituicao = findViewById(R.id.campoInstituicao);
    }

    public void criar(View view) {
        usuario = new Usuario(
                nome.getText().toString(),
                email.getText().toString(),
                senha.getText().toString(),
                profissao.getText().toString(),
                instituicao.getText().toString());
        String s = usuario.getEmail().replaceAll("\\.","");
        if (ocupacao.equals("Professor")){
            mDatabase.child("UsersP").child(s).setValue(usuario)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("passou");
                            voltar();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("falhou");
                        }
                    });
            }
        else
            mDatabase.child("UsersA").child(s).setValue(usuario)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("passou");
                            voltar();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("falhou");
                        }
                    });;
    }

    public void voltar(){super.onBackPressed();}

}
