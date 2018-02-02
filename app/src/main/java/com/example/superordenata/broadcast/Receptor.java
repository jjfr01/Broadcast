package com.example.superordenata.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Receptor extends BroadcastReceiver {

    private int lastState;
    private Date comienzoLlamada;
    private boolean esEntrante;
    private String numeroGuardado;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            numeroGuardado = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        }
        else{
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String numero = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int tipoNumero = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                tipoNumero = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                tipoNumero = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                tipoNumero = TelephonyManager.CALL_STATE_RINGING;
            }

            onCallStateChanged(context, tipoNumero, numero);
        }
    }

    private void logCall(Context context, String numero, String tipo, String fecha){
        Intent intent = new Intent(context, ServicioCliente.class);
        intent.putExtra("estado", tipo);
        intent.putExtra("numero", numero);
        intent.putExtra("fecha", fecha);
        context.startService(intent);
    }

    public void onCallStateChanged(Context context, int estado, String numero) {
        switch (estado) {
            case TelephonyManager.CALL_STATE_RINGING:
                esEntrante = true;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd k:mm", Locale.getDefault());
                comienzoLlamada = new Date();
                String fecha = dateFormat.format(comienzoLlamada);
                numeroGuardado = numero;
                String tipo = "Entrante";
                logCall(context, numeroGuardado, tipo, fecha);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    esEntrante = false;
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd k:mm", Locale.getDefault());
                    comienzoLlamada = new Date();
                    fecha = dateFormat.format(comienzoLlamada);
                    numeroGuardado = numero;
                    tipo = "Saliente";
                    logCall(context, numeroGuardado, tipo, fecha);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd k:mm", Locale.getDefault());
                    comienzoLlamada = new Date();
                    fecha = dateFormat.format(comienzoLlamada);
                    numeroGuardado = numero;
                    tipo = "Perdida";
                    logCall(context, numeroGuardado, tipo, fecha);
                break;
        }
        lastState = estado;
    }
}
