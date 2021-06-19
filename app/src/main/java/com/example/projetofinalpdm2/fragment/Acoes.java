package com.example.projetofinalpdm2.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.projetofinalpdm2.MapsActivity2;
import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.DiasSemana;
import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.R;
import com.example.projetofinalpdm2.Utils.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> escolhas;

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
        escolhas = Arrays.asList(getResources().getStringArray(R.array.apps));
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
        if (singleton.getOcupacaoUsuarioLogado().equals("Professor") && aula.getProfessor().getEmail().equals(singleton.getUsuarioLogado().getEmail())) {
            cadastrarAluno.setText("Compartilhar Aula");
            cadastrarAluno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enviar();
                }
            });
        }else if (singleton.getOcupacaoUsuarioLogado().equals("Professor"))
            cadastrarAluno.setVisibility(View.GONE);
        return view;
    }

    private String stringDiasSemana(){
        String s = "";
        for (DiasSemana d: aula.getDiasSemanaList()){
            if (d.equals(aula.getDiasSemanaList().get(aula.getDiasSemanaList().size()-1)))
                s = s +d.toString()+" ";
            else
                s = s +d.toString()+", ";
        }
        return s;
    }

    private void enviar() {
        String diasSemana = stringDiasSemana();
        String s ="Ola, venho covida-los para aula de "+
                aula.getDisciplina().getNome()+" de nivel "+
                aula.getDisciplina().getNivel()+" que esta programada para ocorrer durante toda semana nos dias de "+
                diasSemana+ "para mais informações baixe o aplicativo AulaoIFTO.";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, s);
        intent.setType("text/plain");
        AlertDialog alertDialog= new AlertDialog
                .Builder(getActivity())
                .setTitle("Escolha o meio de Compartilhamento")
                .setItems(R.array.apps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(escolhas.get(which).equals("Wathsap"))
                            intent.setPackage("com.whatsapp");
                        else if (escolhas.get(which).equals("GMAIL"))
                            intent.setPackage("com.google.android.gm");
                        else if (escolhas.get(which).equals("Mensagem"))
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getContext());
                                intent.setPackage(defaultSmsPackageName);
                            }else{
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setType("vnd.android-dir/mms-sms");
                            }
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }

    public void cadastrarAluno(){
        boolean validador = false;
        if (aula.getAlunos() == null)
            aula.setAlunos(new ArrayList<>());
        for(Usuario u: aula.getAlunos()){
            if(u.equals(singleton.getUsuarioLogado()))
                validador = true;
        }
        if (validador == false){
        aula.getAlunos().add(singleton.getUsuarioLogado());
        String id = aula.getDisciplina().getNome()+aula.getDisciplina().getNivel().toString() + aula.getProfessor().getNome()+aula.getHora()+aula.getMinuto();
        mDatabaseAula.child(id).setValue(aula);
        }
        getActivity().onBackPressed();
    }
}