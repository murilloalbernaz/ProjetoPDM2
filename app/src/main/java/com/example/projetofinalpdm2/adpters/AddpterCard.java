package com.example.projetofinalpdm2.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalpdm2.Model.Disciplina;
import com.example.projetofinalpdm2.R;

import java.util.List;

public class AddpterCard extends RecyclerView.Adapter<AddpterCard.MyViewHolder> {

    List<Disciplina> disciplinaList;


    public AddpterCard(List<Disciplina> disciplinaList) {
        this.disciplinaList = disciplinaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleviewdisciplinas, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText("Nome: "+disciplinaList.get(position).getNome());
        holder.nivel.setText("Nivel: "+disciplinaList.get(position).getNivel().toString());
    }

    @Override
    public int getItemCount() {
        return disciplinaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, nivel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeDisciplina);
            nivel= itemView.findViewById(R.id.nivel);
        }
    }
}
