package com.telefonica.first.tresenraya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.telefonica.first.tresenraya.ServicioId;
public class Principal extends AppCompatActivity {
    public  SharedPreferences sp;
    public static TextView validarNick;
    public static TextView saludo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iniciarTimer();
        sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        String lineaNick=sp.getAll().toString();
        System.out.println("linea nick :"+lineaNick);
        String[] lineaSeparada;
        saludo=(TextView)this.findViewById(R.id.saludo);
        validarNick=(TextView)this.findViewById(R.id.validarNick);
        if(lineaNick.contains("nick")){
            System.out.println("con nick");
            lineaSeparada=lineaNick.substring(lineaNick.indexOf("{")+1,lineaNick.indexOf("}")).split("=");
            if(lineaSeparada[1].equalsIgnoreCase("-")){
                irRegistro();
            }else{
                mostrar ();
            }

        }else{
            System.out.println("Sin nick");
            irRegistro();
        }



    }


    public void irRegistro(View v){
        Intent intent=new Intent(this, SalirActivity.class);
        startActivity(intent);
    }
    public void irRegistro(){
        Intent intent=new Intent(this, SalirActivity.class);
        startActivity(intent);
    }
    public void irJugar(View v){
        Intent intent=new Intent(this, Jugar.class);
        startActivity(intent);
    }
    public void irSalir(){
        Intent intent=new Intent(this, Salir.class);
        startActivity(intent);
    }

    public void iniciarTimer(){
        startService(new Intent(this, ServicioId.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        General.menu(this,id);
        return super.onOptionsItemSelected(item);

    }


    public void mostrar (){
        //creamos el objeto SharedPreferences
        sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        TextView resultado=(TextView)this.findViewById(R.id.saludo);
        TextView validarNick=(TextView)this.findViewById(R.id.validarNick);
        String[] lineaSeparada;
        String resultadoFinal=" ";
        //Recuperamos los datos del xml y los guardamos en un String
        String lineaNick=sp.getAll().toString();
        //Cortamos y pegamos hasta obtener el resultado deseado
        lineaSeparada=lineaNick.substring(lineaNick.indexOf("{")+1,lineaNick.indexOf("}")).split("=");

        resultadoFinal=resultadoFinal+lineaSeparada[1]+"\n";
        //Volcamos el resultado el TextView
        resultado.setText("Bienvenido "+resultadoFinal);
        validarNick.setText("¿No eres "+lineaSeparada[1]+"?");
    }
    @Override protected void onStart() {
        super.onStart();
    }
    public static Handler tostada = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //el valor del segundo parámetro es recogido
            //mediante el atributo arg1

            int id= msg.arg1;
            System.out.println("estamos en el handler");

                saludo.setText("alguien a ganado");
        }
    };
        public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("¿Quieres salir del juego?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
//if user pressed "yes", then he is allowed to exit from application
                    irSalir();
                }
            });
            builder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
//if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
});
                AlertDialog alert=builder.create();
 alert.show();
            }
        }

