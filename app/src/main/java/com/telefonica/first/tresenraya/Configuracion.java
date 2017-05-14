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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Configuracion extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    public static String dificultad="";
    public boolean sonido=true;
    public boolean vibracion=true;
    public ImageView activarsonido;
    public ImageView desactivarsonido;
    public ImageView activarvibracion;
    public ImageView desactivarvibracion;
    public ImageView sonidoOn;
    public ImageView sonidoOff;
    public ImageView vibracionOn;
    public ImageView vibracionOff;
    public  SharedPreferences sp;
    public  SharedPreferences sp2;
    public  SharedPreferences sp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recuperarValoresIniciales();
        configuracionInicial();
    }

    public void itemChecked(View v) {

        radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
        // get selected radio button from radioGroup

                // find the radiobutton by returned id
                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("Facil")) {
                    //System.out.println("el selec es facil");
                      dificultad = "facil";
                } else if (radioButton.getText().toString().equalsIgnoreCase("Media")) {
                    //System.out.println("el selec es media");
                     dificultad = "media";
                } else if (radioButton.getText().toString().equalsIgnoreCase("Dificil")) {
                    //System.out.println("el selec es dificil");
                    dificultad = "dificil";
                }

        sp = this.getSharedPreferences("dificultad", Context.MODE_PRIVATE);
        //Despues hay que poner el objeto en modo editor
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("dificultad", dificultad);
        editor.commit();
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
    public void configuracionInicial(){
        if (sonido) {
            activarsonido = (ImageView) findViewById(R.id.activarSonido);
            desactivarsonido = (ImageView) findViewById(R.id.desactivarSonido);
            sonidoOn=(ImageView) findViewById(R.id.sonidoOn);
            sonidoOff=(ImageView) findViewById(R.id.sonidoOff);
            sonidoOn.setVisibility(View.VISIBLE);
            sonidoOff.setVisibility(View.INVISIBLE);
            activarsonido.setVisibility(View.INVISIBLE);
            desactivarsonido.setVisibility(View.VISIBLE);
        }else{
            activarsonido = (ImageView) findViewById(R.id.activarSonido);
            desactivarsonido = (ImageView) findViewById(R.id.desactivarSonido);
            sonidoOn=(ImageView) findViewById(R.id.sonidoOn);
            sonidoOff=(ImageView) findViewById(R.id.sonidoOff);
            sonidoOn.setVisibility(View.INVISIBLE);
            sonidoOff.setVisibility(View.VISIBLE);
            activarsonido.setVisibility(View.VISIBLE);
            desactivarsonido.setVisibility(View.INVISIBLE);
        }
        if(vibracion){
            activarvibracion = (ImageView) findViewById(R.id.activarVibracion);
            desactivarvibracion = (ImageView) findViewById(R.id.desactivarVibracion);
            vibracionOn=(ImageView) findViewById(R.id.vibracionOn);
            vibracionOff=(ImageView) findViewById(R.id.vibracionOff);
            vibracionOn.setVisibility(View.VISIBLE);
            vibracionOff.setVisibility(View.INVISIBLE);
            activarvibracion.setVisibility(View.INVISIBLE);
            desactivarvibracion.setVisibility(View.VISIBLE);
        }else{
            activarvibracion = (ImageView) findViewById(R.id.activarVibracion);
            desactivarvibracion = (ImageView) findViewById(R.id.desactivarVibracion);
            vibracionOn=(ImageView) findViewById(R.id.vibracionOn);
            vibracionOff=(ImageView) findViewById(R.id.vibracionOff);
            vibracionOn.setVisibility(View.INVISIBLE);
            vibracionOff.setVisibility(View.VISIBLE);
            activarvibracion.setVisibility(View.VISIBLE);
            desactivarvibracion.setVisibility(View.INVISIBLE);
        }
    }

public void activarSonido(View v) {

    if (sonido) {
        activarsonido = (ImageView) findViewById(R.id.activarSonido);
        desactivarsonido = (ImageView) findViewById(R.id.desactivarSonido);
        sonidoOn=(ImageView) findViewById(R.id.sonidoOn);
        sonidoOff=(ImageView) findViewById(R.id.sonidoOff);
        sonidoOn.setVisibility(View.INVISIBLE);
        sonidoOff.setVisibility(View.VISIBLE);
        activarsonido.setVisibility(View.VISIBLE);
        desactivarsonido.setVisibility(View.INVISIBLE);
        sonido = false;
        sp=this.getSharedPreferences("sonido", Context.MODE_PRIVATE);
        //Despues hay que poner el objeto en modo editor
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("sonido","no");
        editor.commit();

    } else {
        activarsonido = (ImageView) findViewById(R.id.activarSonido);
        desactivarsonido = (ImageView) findViewById(R.id.desactivarSonido);
        sonidoOn=(ImageView) findViewById(R.id.sonidoOn);
        sonidoOff=(ImageView) findViewById(R.id.sonidoOff);
        sonidoOn.setVisibility(View.VISIBLE);
        sonidoOff.setVisibility(View.INVISIBLE);
        activarsonido.setVisibility(View.INVISIBLE);
        desactivarsonido.setVisibility(View.VISIBLE);
        sonido = true;
        sp=this.getSharedPreferences("sonido", Context.MODE_PRIVATE);
        //Despues hay que poner el objeto en modo editor
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("sonido","si");
        editor.commit();
    }
}
    public void activarVibracion(View v) {

        if (sonido) {
            activarvibracion = (ImageView) findViewById(R.id.activarVibracion);
            desactivarvibracion = (ImageView) findViewById(R.id.desactivarVibracion);
            vibracionOn=(ImageView) findViewById(R.id.vibracionOn);
            vibracionOff=(ImageView) findViewById(R.id.vibracionOff);
            vibracionOn.setVisibility(View.INVISIBLE);
            vibracionOff.setVisibility(View.VISIBLE);
            activarvibracion.setVisibility(View.VISIBLE);
            desactivarvibracion.setVisibility(View.INVISIBLE);
            sonido = false;
            sp=this.getSharedPreferences("vibracion", Context.MODE_PRIVATE);
            //Despues hay que poner el objeto en modo editor
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("vibracion","no");
            editor.commit();

        } else {
            activarvibracion = (ImageView) findViewById(R.id.activarVibracion);
            desactivarvibracion = (ImageView) findViewById(R.id.desactivarVibracion);
            vibracionOn=(ImageView) findViewById(R.id.vibracionOn);
            vibracionOff=(ImageView) findViewById(R.id.vibracionOff);
            vibracionOn.setVisibility(View.VISIBLE);
            vibracionOff.setVisibility(View.INVISIBLE);
            activarvibracion.setVisibility(View.INVISIBLE);
            desactivarvibracion.setVisibility(View.VISIBLE);
            sonido = true;
            sp=this.getSharedPreferences("vibracion", Context.MODE_PRIVATE);
            //Despues hay que poner el objeto en modo editor
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("vibracion","si");
            editor.commit();
        }
    }
public void recuperarValoresIniciales(){
    sp=this.getSharedPreferences("sonido", Context.MODE_PRIVATE);
    String lineaSonido=sp.getAll().toString();
    System.out.println("linea sonido:"+lineaSonido);
    String[] lineaSeparada;

    if(lineaSonido.contains("sonido")){
        System.out.println("con sonido");
        lineaSeparada=lineaSonido.substring(lineaSonido.indexOf("{")+1,lineaSonido.indexOf("}")).split("=");
        if(lineaSeparada[1].equalsIgnoreCase("si")){
            sonido=true;
        }else{
            sonido=false ;
        }
    }else{
        System.out.println("Sin sonido");
        sonido=true;
    }

    sp2=this.getSharedPreferences("vibracion", Context.MODE_PRIVATE);
    String lineaVibracion=sp2.getAll().toString();
    System.out.println("linea vibracion:"+lineaVibracion);
    String[] lineaSeparada2;

    if(lineaVibracion.contains("vibracion")){
        System.out.println("con vibracion");
        lineaSeparada2=lineaVibracion.substring(lineaVibracion.indexOf("{")+1,lineaVibracion.indexOf("}")).split("=");
        if(lineaSeparada2[1].equalsIgnoreCase("si")){
            vibracion=true;
        }else{
            vibracion=false ;
        }
    }else{
        System.out.println("Sin vibracion");
        vibracion=true;
    }
    sp3=this.getSharedPreferences("dificultad", Context.MODE_PRIVATE);
    String lineaDificultad=sp3.getAll().toString();
    System.out.println("linea dificultad:"+lineaDificultad);
    String[] lineaSeparada3;

    if(lineaDificultad.contains("dificultad")){
        System.out.println("con dificultad");
        lineaSeparada3=lineaDificultad.substring(lineaDificultad.indexOf("{")+1,lineaDificultad.indexOf("}")).split("=");
        if(lineaSeparada3[1].equalsIgnoreCase("facil")){
            radioButton = (RadioButton) findViewById(R.id.facil);
            radioButton.setChecked(true);
            dificultad="facil";
        }else if(lineaSeparada3[1].equalsIgnoreCase("media")){
            radioButton = (RadioButton) findViewById(R.id.media);
            radioButton.setChecked(true);
            dificultad="media";
        }else{
            radioButton = (RadioButton) findViewById(R.id.dificil);
            radioButton.setChecked(true);
            dificultad="dificil";
        }
    }else{
        System.out.println("Sin dificultad");
        dificultad="media";
        sp3=this.getSharedPreferences("dificultad", Context.MODE_PRIVATE);
        //Despues hay que poner el objeto en modo editor
        SharedPreferences.Editor editor=sp3.edit();
        editor.putString("dificultad",dificultad);
        editor.commit();

    }
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
                finishAffinity();
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