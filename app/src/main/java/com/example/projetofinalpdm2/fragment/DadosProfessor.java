package com.example.projetofinalpdm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DadosProfessor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DadosProfessor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Usuario professor;

    public DadosProfessor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teste2.
     */
    // TODO: Rename and change types and number of parameters
    public static DadosProfessor newInstance(Usuario professor) {
        DadosProfessor fragment = new DadosProfessor();
        Bundle args = new Bundle();
        args.putSerializable("porfessor",professor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            professor = (Usuario) getArguments().getSerializable("porfessor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_dados_professor, container, false);
        TextView nome, email, istituicao, profissao;
        nome = view.findViewById(R.id.nomeProfessor);
        email = view.findViewById(R.id.emailProfessor);
        istituicao = view.findViewById(R.id.istituicaoProfesspr);
        profissao = view.findViewById(R.id.diasSemanaDisciplina);
        nome.setText("Nome: "+professor.getNome());
        email.setText("Email: "+professor.getEmail());
        istituicao.setText("Istituição: "+professor.getInstituicao());
        profissao.setText("Profissao: "+professor.getProfissao());
        return view;
    }
}