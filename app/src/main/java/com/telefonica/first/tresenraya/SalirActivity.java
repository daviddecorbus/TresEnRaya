package com.telefonica.first.tresenraya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SalirActivity extends AppCompatActivity {
    public  SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        borrar();
        sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        String lineaNick=sp.getAll().toString();
        String[] lineaSeparada;
        TextView resultado=(TextView)this.findViewById(R.id.nick);
        lineaSeparada=lineaNick.substring(lineaNick.indexOf("{")+1,lineaNick.indexOf("}")).split("=");
        if(lineaSeparada[1].equalsIgnoreCase("-")){
            resultado.setText("Hola Jugador");
        }else{
            mostrar ();
        }

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
    public void registrar(View v){
        //Primero creamos el objeto SharedPreferences
        sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        //Despues hay que poner el objeto en modo editor
        SharedPreferences.Editor editor=sp.edit();
        //Recuperamos los valores de los dos EditText
        EditText caja = (EditText) this.findViewById(R.id.editNick);
        String t = caja.getText().toString();
        TextView resultado=(TextView)this.findViewById(R.id.nick);


        boolean introducir=true;
        //Revisamos que los EditText estén rellenos
        if(t.equalsIgnoreCase("")){
            Toast.makeText(this, "Introduce tu nick", Toast.LENGTH_SHORT).show();
            introducir=false;
        }
        //Si están rellenos introducimos los datos, los guardamos y vaciamos las cajas
        if(introducir==true) {
            editor.putString("nick",t.trim());
            editor.commit();
            caja.setText("");
            irPrincipal();
        }
    }


    public void mostrar (){
        //creamos el objeto SharedPreferences
        sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        TextView resultado=(TextView)this.findViewById(R.id.nick);
        String[] lineaSeparada;
        String resultadoFinal=" ";
        //Recuperamos los datos del xml y los guardamos en un String
        String lineaNick=sp.getAll().toString();
        //Cortamos y pegamos hasta obtener el resultado deseado
        lineaSeparada=lineaNick.substring(lineaNick.indexOf("{")+1,lineaNick.indexOf("}")).split("=");

        resultadoFinal=resultadoFinal+lineaSeparada[1]+"\n";
        //Volcamos el resultado el TextView
        resultado.setText("Bienvenido "+resultadoFinal);
    }
    public  void borrar(){
        //Para borrar el archivo entero primero tenemos que borrarlo y luego guardar
        sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("nick","-");
        editor.commit();
    }
    public void irPrincipal(){
        Intent intent=new Intent(this, Principal.class);
        startActivity(intent);
    }
    public void irSalir(){
        Intent intent=new Intent(this, Salir.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("¿Quieres salir del juego?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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