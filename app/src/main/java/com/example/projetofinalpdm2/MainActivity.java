package com.example.projetofinalpdm2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Professor(View view) {
        Intent i = new Intent(this, Activity2Login.class);
        i.putExtra("ocupacao", "Professor");
        startActivity(i);
    }

    public void aluno(View view) {
        Intent i = new Intent(this, Activity2Login.class);
        i.putExtra("ocupacao", "Aluno");
        startActivity(i);
    }
}