package com.example.projetofinalpdm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.R;
import com.example.projetofinalpdm2.adpters.AdapterAlunosDetalheAula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaAluno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaAluno extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private List<Usuario> alunos;

    public ListaAluno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teste3.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaAluno newInstance(ArrayList<Usuario> alunos) {
        ListaAluno fragment = new ListaAluno();
        Bundle args = new Bundle();
        args.putSerializable("alunos", alunos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alunos = (List<Usuario>) getArguments().getSerializable("alunos");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_alunos, container, false);
        RecyclerView.Adapter adapter = new AdapterAlunosDetalheAula(alunos);
        RecyclerView recyclerView = view.findViewById(R.id.listaAlunosCadastrados);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}