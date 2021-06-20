package com.example.projetofinalpdm2.Utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projetofinalpdm2.Activity2;
import com.example.projetofinalpdm2.Activity2Login;
import com.example.projetofinalpdm2.MainActivity;
import com.example.projetofinalpdm2.Model.Aula;
import com.example.projetofinalpdm2.Model.DiasSemana;
import com.example.projetofinalpdm2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyBrodCastReciver extends BroadcastReceiver {
    ArrayList<Aula> aulasDoDia;
    String s = "quantidade de Aulas Agendadas para hoje: ";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra("listaAula");
        ArrayList<Aula> aulaList = (ArrayList<Aula>) bundle.getSerializable("lista1");
        filtraAulas(aulaList);
        if (aulasDoDia != null && aulasDoDia.size()>0){
            s = s + String.valueOf(aulasDoDia.size());
            Intent intent1 = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pi = PendingIntent.getActivity(context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat
                    .Builder(context, Activity2Login.CHANEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Aplicativo AULÃOIFTO")
                    .setContentText(s)
                    .setContentIntent(pi)
                    .setPriority(NotificationCompat.PRIORITY_HIGH).build();
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(10, notification);
        }
    }

    private void filtraAulas(List<Aula> aulas) {
        aulasDoDia = new ArrayList<>();
        DiasSemana diasSemana = pegarReferenciar();
        if(aulas != null){
            for (Aula a: aulas){
                if (a.getDiasSemanaList() != null){
                    for (DiasSemana d: a.getDiasSemanaList()){
                        if (d.toString().equals(diasSemana.toString()))
                            aulasDoDia.add(a);
                    }
                }
            }
        }
    }

    private DiasSemana pegarReferenciar() {
        int i =Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (i==1)
            return DiasSemana.DOMINGO;
        if (i==2)
            return DiasSemana.SEGUNDA;
        if (i==3)
            return DiasSemana.TERÇA;
        if (i==4)
            return DiasSemana.QUARTA;
        if (i==5)
            return DiasSemana.QUINTA;
        if (i==6)
            return DiasSemana.SEXTA;
        if (i==7)
            return DiasSemana.SABADO;
        return null;
    }
}
