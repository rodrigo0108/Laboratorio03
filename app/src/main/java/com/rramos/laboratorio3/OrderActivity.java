package com.rramos.laboratorio3;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private Spinner spinner_pizzas;
    private CheckBox check_mozarella,check_jamon;
    private String pizzas[] = {"Americana","Meet lover","Hawaiana","Super suprema"};
    private String pizza_seleccionada ="";
    private double precio,precio2;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        spinner_pizzas = (Spinner)findViewById(R.id.spinner_pizzas);
        check_mozarella = (CheckBox)findViewById(R.id.checkBox_mozarella);
        check_jamon = (CheckBox)findViewById(R.id.checkBox_jamon);
        radioGroup =  (RadioGroup) findViewById(R.id.grupo_masa);

        ArrayAdapter<String> spinerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,pizzas);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pizzas.setAdapter(spinerAdapter);
        spinner_pizzas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pizza_selected= adapterView.getSelectedItemPosition();
                String pizza_selected2 = adapterView.getItemAtPosition(i).toString();
                pizza_seleccionada=pizza_selected2;
                switch (pizza_selected){
                    case 0:
                        precio = 40;
                        break;
                    case 1:
                        precio = 60;
                        break;
                    case 2:

                        precio = 45;
                        break;
                    case 3:
                        precio = 65;
                        break;

                }
                String asd=String.valueOf(precio);

                Toast.makeText(adapterView.getContext(),"El precio de la pizza "+pizza_selected2+" es: "+precio,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void androidCheckMasa(View view) {
        switch (view.getId()){
            case R.id.checkBox_mozarella:
                CheckBox checkBox = (CheckBox) view;
                if(checkBox.isChecked()){
                    precio2= precio2+8;
                }else {
                    precio2= precio2-8;
                }

                break;
            case R.id.checkBox_jamon:
                CheckBox checkBox2 = (CheckBox) view;
                if(checkBox2.isChecked()){
                    precio2= precio2+12;
                }else {
                    precio2= precio2-12;
                }
                break;
        }
    }

    public void androidRespuesta(final View view) {

        double suma=precio+precio2;
        String suma_co=String.valueOf(suma);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(radioButtonId);

        if (radioButtonId == -1){
            Toast.makeText(this, "Es importante que seleccione el tipo de masa", Toast.LENGTH_LONG).show();
        }
        else{
            alertDialog.setTitle("Confirmación de pedido");
            alertDialog.setMessage("Su pedido de pizza "+pizza_seleccionada+" con "+radioButton.getText()+" es de "+ suma_co + " + IGV ,está en proceso de envío");
            // Alert dialog button
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Alert dialog action goes here
                            dialog.dismiss();// use dismiss to cancel alert dialog
                        }
                    });
            alertDialog.show();

        }

        //Codigo de notificación
        final Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Comprobante de pedido")
                .setContentText("Su pedido a sido recibido, estaremos llegando en media hora , gracias por elegirnos")
                .setSmallIcon(R.drawable.ic_cellphone)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .build();

        // Notification manager
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationManager.notify(0, notification);
            }
        },10000);

    }
}
