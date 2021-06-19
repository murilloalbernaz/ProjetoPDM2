package com.example.projetofinalpdm2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projetofinalpdm2.MapsActivity2;
import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.R;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Acoes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Acoes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Aula aula;
    private Singleton singleton = Singleton.getInstance();
    private DatabaseReference mDatabaseAula;

    public Acoes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teste4.
     */
    // TODO: Rename and change types and number of parameters
    public static Acoes newInstance(Aula aula) {
        Acoes fragment = new Acoes();
        Bundle args = new Bundle();
        args.putSerializable("aula", aula);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            aula = (Aula) getArguments().getSerializable("aula");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acoes, container, false);
        mDatabaseAula = FirebaseDatabase.getInstance().getReference().child("Aulas");
        Button localizacao, cadastrarAluno, voltar;
        localizacao = view.findViewById(R.id.botaoLocalizacaoFuncoes);
        cadastrarAluno = view.findViewById(R.id.botaoCadastrarAulaFuncoes);
        voltar = view.findViewById(R.id.botaoVoltarFuncoes);
        localizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity2.class);
                intent.putExtra("latitude", aula.getLatLng().getLatitude());
                intent.putExtra("longitude", aula.getLatLng().getLongitude());
                startActivity(intent);
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        cadastrarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAluno();
            }
        });
        return view;
    }
    public void cadastrarAluno(){
        if (aula.getAlunos() == null)
            aula.setAlunos(new ArrayList<>());
        if(!aula.getAlunos().contains(singleton.getUsuarioLogado())){
            aula.getAlunos().add(singleton.getUsuarioLogado());
            String id = aula.getDisciplina().getNome()+aula.getDisciplina().getNivel().toString() + aula.getProfessor().getNome()+aula.getHora()+aula.getMinuto();
            mDatabaseAula.child(id).setValue(aula);
        }
        getActivity().onBackPressed();
    }
}