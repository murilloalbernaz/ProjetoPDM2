package com.example.projetofinalpdm2.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalpdm2.Model.Usuario;
import com.example.projetofinalpdm2.R;

import java.util.List;

public class AdapterAlunosDetalheAula extends RecyclerView.Adapter<AdapterAlunosDetalheAula.MyViewHolder> {
    List<Usuario> alunos;

    public AdapterAlunosDetalheAula(List<Usuario> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleviewalunoslist, parent, false);
        AdapterAlunosDetalheAula.MyViewHolder myViewHolder = new AdapterAlunosDetalheAula.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(alunos.get(position).getNome());
        holder.email.setText(alunos.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeAluno);
            email = itemView.findViewById(R.id.emailAluno);
        }
    }
}
