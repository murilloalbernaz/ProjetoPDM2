package com.example.projetofinalpdm2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.example.projetofinalpdm2.fragment.DadosAula;
import com.example.projetofinalpdm2.fragment.DadosProfessor;
import com.example.projetofinalpdm2.fragment.ListaAluno;
import com.example.projetofinalpdm2.fragment.Acoes;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity {
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pagerAdapter;
    private TabLayout tableLayout;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseAula;
    Singleton singleton = Singleton.getInstance();
    private Aula aula;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentactvity);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabaseAula = mDatabase.child("Aulas");
        aula = (Aula) getIntent().getSerializableExtra("Aula");
        viewPager2 = findViewById(R.id.viewPage2);
        pagerAdapter = new ScreenSlidePageAdpter(this);
        viewPager2.setAdapter(pagerAdapter);
        tableLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tableLayout, viewPager2, (tab, position) -> tab.setText(titulosGuia(position))).attach();
    }

    private String titulosGuia(int position) {
        switch (position){
            case 0:
                return "Dados Aula";
            case 1:
                return "Dados Professor";
            case 2:
                return "Alunos";
            case 3:
                return "Funções";
        }
        return null;
    }


    private class ScreenSlidePageAdpter extends FragmentStateAdapter{


        public ScreenSlidePageAdpter(@NonNull androidx.fragment.app.FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return DadosAula.newInstance(aula);
                case 1:
                    return DadosProfessor.newInstance(aula.getProfessor());
                case 2:
                    ArrayList listaAlunos = new ArrayList<>();
                    if (aula.getAlunos() != null)
                        listaAlunos.addAll(aula.getAlunos());
                    return ListaAluno.newInstance(listaAlunos);
                case 3:
                    return Acoes.newInstance(aula);
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
