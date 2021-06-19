package com.example.projetofinalpdm2.adpters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalpdm2.FragmentActivity;
import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.R;


import java.util.List;

public class AdapterAulasDashboard  extends RecyclerView.Adapter<AdapterAulasDashboard.MyViewHolder> {
    private List<Aula> aulaList;
    private static Context c;

    public AdapterAulasDashboard(List<Aula> aulaList, Context context) {
        this.aulaList = aulaList;
        this.c = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleviewaulasdashboard, parent, false);

        AdapterAulasDashboard.MyViewHolder myViewHolder = new AdapterAulasDashboard.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(aulaList.get(position).getDisciplina().getNome());
        holder.hora.setText("horario: "+aulaList.get(position).getHora()+":"+aulaList.get(position).getMinuto());
        holder.nivel.setText(aulaList.get(position).getDisciplina().getNivel().toString());
    }

    @Override
    public int getItemCount() {
        return aulaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome, hora, nivel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeDisciplinaAula);
            hora = itemView.findViewById(R.id.horaAulas);
            nivel = itemView.findViewById(R.id.nivelDisciplinaAula);
            itemView.setOnClickListener(
                    (V)->{
                        int position = getAdapterPosition();
                        Intent intent = new Intent(c, FragmentActivity.class);
                        intent.putExtra("Aula",aulaList.get(position));
                        c.startActivity(intent);
                    }
                    );
        }
    }
}
