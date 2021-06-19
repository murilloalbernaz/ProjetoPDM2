package com.example.projetofinalpdm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.DiasSemana;
import com.example.projetofinalpdm2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DadosAula#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DadosAula extends Fragment {

    // TODO: Rename and change types of parameters
    private Aula aula;

    public DadosAula() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teste1.
     */
    // TODO: Rename and change types and number of parameters
    public static DadosAula newInstance(Aula aula) {
        DadosAula fragment = new DadosAula();
        Bundle args = new Bundle();
        args.putSerializable("Aula", aula);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            aula =(Aula) getArguments().getSerializable("Aula");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_aula, container, false);
        TextView nome, nivel, horario, dias;
        nome = view.findViewById(R.id.nomeDisciplina);
        nivel = view.findViewById(R.id.nivelDisciplina);
        horario =view.findViewById(R.id.horarioDisciplina);
        dias = view.findViewById(R.id.diasSemanaDisciplina);
        nome.setText("Nome: "+ aula.getDisciplina().getNome());
        nivel.setText("Nivel: "+aula.getDisciplina().getNivel().toString());
        horario.setText("Inicio: "+aula.getHora()+":"+aula.getMinuto());
        dias.setText(construirStringDias());
        return view;
    }

    private String construirStringDias() {
        String s = "Dias: ";

        for (DiasSemana d: aula.getDiasSemanaList()){
            if(d.equals(aula.getDiasSemanaList().get(aula.getDiasSemanaList().size()-1)))
                s = s + d.toString()+".";
            else
                s = s + d.toString()+", ";
        }
        return s;
    }


}