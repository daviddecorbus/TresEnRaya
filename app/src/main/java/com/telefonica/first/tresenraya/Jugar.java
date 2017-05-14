package com.telefonica.first.tresenraya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Random;

public class Jugar extends AppCompatActivity {
    public String resultado="";
    public int[] tablero = new int [9];
    public boolean jugar=true;
    public boolean playJugador=true;
    public MediaPlayer humano;
    public MediaPlayer robot;
    public MediaPlayer ganaRobot;
    public MediaPlayer ganaHumano;
    public String nick;
    public String partida="";
    public String fecha="";
    public int contadorEmpate =0;
    public boolean sonido=true;
    public boolean vibracion=true;
    public String dificultad="media";
    public  SharedPreferences sp;
    public  SharedPreferences sp2;
    public  SharedPreferences sp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //recuperamos el nick
        SharedPreferences sp=this.getSharedPreferences("nick", Context.MODE_PRIVATE);
        String lineaNick=sp.getAll().toString();
        String[] lineaSeparada;
        lineaSeparada=lineaNick.substring(lineaNick.indexOf("{")+1,lineaNick.indexOf("}")).split("=");
        nick=lineaSeparada[1];
        recuperarValoresIniciales();
        //creamos los sonidos
        cargarSonidos();
        //random para decidir quien empieza la partida

        Random r=new Random();
        if(r.nextInt(2)+1==1){
            playJugador=false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pensarRobot();
                }
            }, 500);
        }
        //reinicio de tablero
        for (int i=0; i<9; i++){
            tablero[i]=0;
        }


    }
    //cargar sonidos
    public void cargarSonidos(){
        humano = MediaPlayer.create(this, R.raw.humano);
        robot = MediaPlayer.create(this, R.raw.robot);
        ganaRobot = MediaPlayer.create(this, R.raw.ganarrobot);
        ganaHumano = MediaPlayer.create(this, R.raw.ganarhumano);
    }

//metodo para recuperar los valores de sonido y vibración guardados en sharepreferences
    private void recuperarValoresIniciales() {
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
                dificultad="facil";
            }else if(lineaSeparada3[1].equalsIgnoreCase("media")){
                dificultad="media";
            }else{
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
    //checkWinPlayer y checkWinRobot son utilizadas por pensarRobot para decidir cual es el mejor movimiento
    public boolean checkWinPlayer() {
            System.out.println(tablero.toString());
            if ((tablero[0] == 1 && tablero[1] == 1 && tablero[2] == 1) ||
                    (tablero[3] == 1 && tablero[4] == 1 && tablero[5] == 1) ||
                    (tablero[6] == 1 && tablero[7] == 1 && tablero[8] == 1) ||
                    (tablero[0] == 1 && tablero[3] == 1 && tablero[6] == 1) ||
                    (tablero[1] == 1 && tablero[4] == 1 && tablero[7] == 1) ||
                    (tablero[2] == 1 && tablero[5] == 1 && tablero[8] == 1) ||
                    (tablero[0] == 1 && tablero[4] == 1 && tablero[8] == 1) ||
                    (tablero[2] == 1 && tablero[4] == 1 && tablero[6] == 1)) {

                return true;
            }
        return false;
    }
    public boolean checkWinRobot(){
             if((tablero[0]==2&&tablero[1]==2&&tablero[2]==2) ||
                    (tablero[3]==2&&tablero[4]==2&&tablero[5]==2) ||
                    (tablero[6]==2&&tablero[7]==2&&tablero[8]==2) ||
                    (tablero[0]==2&&tablero[3]==2&&tablero[6]==2) ||
                    (tablero[1]==2&&tablero[4]==2&&tablero[7]==2) ||
                    (tablero[2]==2&&tablero[5]==2&&tablero[8]==2) ||
                    (tablero[0]==2&&tablero[4]==2&&tablero[8]==2) ||
                    (tablero[2]==2&&tablero[4]==2&&tablero[6]==2)) {

                return true;
            }
         return false;
    }
    //checkWinPlayer y checkWinRobot son utilizadas para revisar el tablero a ver si algún jugador a ganado, se llaman cada vez que se hace un movimiento
    public boolean checkWinPlayer1() {
        System.out.println(tablero.toString());
        if ((tablero[0] == 1 && tablero[1] == 1 && tablero[2] == 1) ||
                (tablero[3] == 1 && tablero[4] == 1 && tablero[5] == 1) ||
                (tablero[6] == 1 && tablero[7] == 1 && tablero[8] == 1) ||
                (tablero[0] == 1 && tablero[3] == 1 && tablero[6] == 1) ||
                (tablero[1] == 1 && tablero[4] == 1 && tablero[7] == 1) ||
                (tablero[2] == 1 && tablero[5] == 1 && tablero[8] == 1) ||
                (tablero[0] == 1 && tablero[4] == 1 && tablero[8] == 1) ||
                (tablero[2] == 1 && tablero[4] == 1 && tablero[6] == 1)) {
            TextView mostrarGanador= (TextView) findViewById(R.id.mensajesSistema);
            mostrarGanador.setText(nick.toUpperCase() +" GANA!!");
            resultado="ganado";
            ServicioId.miId++;
            if(vibracion) {
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                v.vibrate(1000);
            }
            fecha=obtenerFecha();
            guardarPartida();
            cargar();
            if(sonido) {
                ganaHumano.start();
            }
            jugar=false;
            return true;
        }
        return false;
    }
    public boolean checkWinRobot1(){
        if((tablero[0]==2&&tablero[1]==2&&tablero[2]==2) ||
                (tablero[3]==2&&tablero[4]==2&&tablero[5]==2) ||
                (tablero[6]==2&&tablero[7]==2&&tablero[8]==2) ||
                (tablero[0]==2&&tablero[3]==2&&tablero[6]==2) ||
                (tablero[1]==2&&tablero[4]==2&&tablero[7]==2) ||
                (tablero[2]==2&&tablero[5]==2&&tablero[8]==2) ||
                (tablero[0]==2&&tablero[4]==2&&tablero[8]==2) ||
                (tablero[2]==2&&tablero[4]==2&&tablero[6]==2)) {
            TextView mostrarGanador = (TextView) findViewById(R.id.mensajesSistema);
            mostrarGanador.setText("ROBOT GANA!!");
            ServicioId.miId++;
            resultado="perdido";
            if(vibracion) {
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                v.vibrate(1000);
            }
            fecha=obtenerFecha();
            guardarPartida();
            cargar();
            if(sonido) {
                ganaRobot.start();
            }
            jugar=false;
            return true;
        }
        return false;
    }
//funcion para reiniciar tablero e iniciar nueva partida
  public void reinicio(){
      fecha="";
      Random r=new Random();
      playJugador=true;
      contadorEmpate =0;
    ImageView img= (ImageView) findViewById(R.id.img1);
       img.setImageResource(R.drawable.cuadradoblanco);

            ImageView img2= (ImageView) findViewById(R.id.img2);
            img2.setImageResource(R.drawable.cuadradoblanco);
            ImageView img3= (ImageView) findViewById(R.id.img3);
            img3.setImageResource(R.drawable.cuadradoblanco);
            ImageView img4= (ImageView) findViewById(R.id.img4);
            img4.setImageResource(R.drawable.cuadradoblanco);
            ImageView img5= (ImageView) findViewById(R.id.img5);
            img5.setImageResource(R.drawable.cuadradoblanco);
            ImageView img6= (ImageView) findViewById(R.id.img6);
            img6.setImageResource(R.drawable.cuadradoblanco);
            ImageView img7= (ImageView) findViewById(R.id.img7);
            img7.setImageResource(R.drawable.cuadradoblanco);
            ImageView img8= (ImageView) findViewById(R.id.img8);
            img8.setImageResource(R.drawable.cuadradoblanco);
            ImageView img9= (ImageView) findViewById(R.id.img9);
            img9.setImageResource(R.drawable.cuadradoblanco);
      for (int i=0; i<9; i++){
          tablero[i]=0;
      }

      if(r.nextInt(2)+1==1){
          Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
              public void run() {
                  pensarRobot();
              }
          }, 500);
      }
      TextView mensajeSistema = (TextView) findViewById(R.id.mensajesSistema);
      mensajeSistema.setText("");
      jugar=true;
   }
//funciones que recogen los movimientos del jugador humano e inician el movimiento del bot al finalizar
    public void jugador1(View v) {
        if (jugar == true&&playJugador==true) {

            if (tablero[0] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador = false;
                contadorEmpate++;
                tablero[0] = 1;
                ImageView img = (ImageView) findViewById(R.id.img1);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pensarRobot();
                        }
                    }, 500);
            }
        }
    }
    public void jugador2(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[1] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[1] = 1;
                ImageView img = (ImageView) findViewById(R.id.img2);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador3(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[2] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[2] = 1;
                ImageView img = (ImageView) findViewById(R.id.img3);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador4(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[3] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[3] = 1;
                ImageView img = (ImageView) findViewById(R.id.img4);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador5(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[4] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[4] = 1;
                ImageView img = (ImageView) findViewById(R.id.img5);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador6(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[5] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[5] = 1;
                ImageView img = (ImageView) findViewById(R.id.img6);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador7(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[6] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[6] = 1;
                ImageView img = (ImageView) findViewById(R.id.img7);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador8(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[7] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador=false;
                contadorEmpate++;
                tablero[7] = 1;
                ImageView img = (ImageView) findViewById(R.id.img8);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pensarRobot();
                    }
                }, 500);
            }
        }
    }
    public void jugador9(View v){
        if (jugar == true&&playJugador==true) {

            if (tablero[8] == 0) {
                if(sonido){
                    humano.start();
                }
                playJugador = false;
                contadorEmpate++;
                tablero[8] = 1;
                ImageView img = (ImageView) findViewById(R.id.img9);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pensarRobot();
                        }
                    }, 500);
            }
        }
    }
    public void pensarRobot(){
        if(contadorEmpate <8){
            pensarRobot2();
        }else if(contadorEmpate==8){
            for (int i = 0; i < 9; i++) {
                if (tablero[i] == 0) {
                    tablero[i] = 2;
                    if (checkWinRobot()) {
                        jugarRobot(i);
                        checkWinRobot1();

                    } else {
                        jugarRobot(i);
                        empate();

                    }
                }

            }
        }else{
            empate();
        }
    }
//pensarRobot decide cuál es el mejor movimiento y según que decida llama a jugarRobot
public boolean pensarRobot2() {
    if(contadorEmpate <8){
    playJugador=true;
    Random r=new Random();
    if (jugar) {
        contadorEmpate++;
        if(sonido){
            robot.start();
        }
        for (int i = 0; i < 9; i++) {
            if (tablero[i] == 0) {
                tablero[i] = 2;
                if (checkWinRobot()) {
                    jugarRobot(i);
                    checkWinRobot1();
                    return true;
                } else {
                    tablero[i] = 0;

                }
            }

        }
        for (int i = 0; i < 9; i++) {
            if (tablero[i] == 0) {
                tablero[i] = 1;
                if (checkWinPlayer() == true) {
                    jugarRobot(i);
                    return true;
                } else {
                    tablero[i] = 0;

                }
            }

        }

        if (tablero[4] == 0) {
            if(dificultad.equalsIgnoreCase("facil")){
                int f=0;
                while(f<1){
                    int c=r.nextInt(8);
                   if(tablero[c]==0){
                       jugarRobot(c);
                       f++;
                   }

                }
                return true;
            }else if(dificultad.equalsIgnoreCase("media")) {
                if(r.nextInt(3)==1){
                    for (int i = 6; i >=0; i--) {
                        if (tablero[i] == 0) {
                            jugarRobot(i);
                            return true;
                        }
                    }

                }else{
                    jugarRobot(4);
                    return true;
                }
            }else{
                jugarRobot(4);
                return true;
            }
        }
        if (tablero[7] == 1) {
            for (int i = 3; i < 9; i++) {
                if (tablero[i] == 0) {
                    jugarRobot(i);
                    return true;
                }
            }
        }
        if (tablero[3] == 1) {
            for (int i = 0; i < 9; i++) {
                if (tablero[i] == 0) {
                    jugarRobot(i);
                    return true;
                }
            }
        }

        if (tablero[5] == 1) {
            for (int i = 0; i < 9; i++) {
                if (tablero[i] == 0) {
                    jugarRobot(i);
                    return true;
                }
            }
        }
        if (tablero[8] == 1) {
            for (int i = 2; i < 9; i++) {
                if (tablero[i] == 0) {
                    jugarRobot(i);
                    return true;
                }
            }
        }
        if (tablero[1] == 1 && tablero[8] == 0) {
            jugarRobot(8);
        } else {
            int d=0;
            if(dificultad.equalsIgnoreCase("facil")){
                int facil=r.nextInt(4);
                if(facil==0){
                    d=1;
                }else if(facil==1){
                    d=3;
                }else if(facil==2){
                    d=5;
                }
                else{
                    d=2;
                }

            }else if(dificultad.equalsIgnoreCase("media")){
                d = r.nextInt(2);
            }else{
                d=0;
            }
            for ( int i = d; i < 9; i++) {
                if (tablero[i] == 0) {
                    jugarRobot(i);
                    return true;
                }
            }


        }
    }
    }
    return true;
}
//Metodo para cuando la partida acaba en empate
    public void empate(){
        TextView mensajeSistema = (TextView) findViewById(R.id.mensajesSistema);
        mensajeSistema.setText("¡PARTIDA EMPATADA!");
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(vibracion){
            v.vibrate(600);
        }


    }

//movimiento de robot en el tablero
public void jugarRobot(int i){

    tablero[i]=2;
    if(i==0) {
        ImageView img = (ImageView) findViewById(R.id.img1);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==1) {
        ImageView img = (ImageView) findViewById(R.id.img2);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==2) {
        ImageView img = (ImageView) findViewById(R.id.img3);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==3) {
        ImageView img = (ImageView) findViewById(R.id.img4);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==4) {
        ImageView img = (ImageView) findViewById(R.id.img5);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==5) {
        ImageView img = (ImageView) findViewById(R.id.img6);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==6) {
        ImageView img = (ImageView) findViewById(R.id.img7);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==7) {
        ImageView img = (ImageView) findViewById(R.id.img8);
        img.setImageResource(R.drawable.circulo);
    }
    if(i==8) {
        ImageView img = (ImageView) findViewById(R.id.img9);
        img.setImageResource(R.drawable.circulo);
    }

}
    public void irSalir(){
        Intent intent=new Intent(this, Salir.class);
        startActivity(intent);
    }
//método para que salte una pantalla cuando pulsas el boton atras del móvil
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
    public void reinicioPressed(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("¿Quieres reiniciar la partida?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
//if user pressed "yes", then he is allowed to exit from application
                reinicio();
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
    //función para guardar el tablero en un string
    public void guardarPartida() {
        partida = "";
        for (int i = 0; i < 9; i++) {
            if (i == 8) {
                if (tablero[i] == 0) {
                    partida = partida +  "-";
                } else if (tablero[i] == 1) {
                    partida = partida +  "X";
                } else if (tablero[i] == 2) {
                    partida = partida + "O";
                }
            } else if (tablero[i] != 8) {
                if (tablero[i] == 0) {
                    partida = partida + "-,";
                } else if (tablero[i] == 1) {
                    partida = partida + "X,";
                } else if (tablero[i] == 2) {
                    partida = partida + "O" + ",";
                }
            }
            System.out.println("la partida guardada es: " + partida);
        }
    }

//metodo para reproducir la partida

   /*public void reproducirPartida(View v){
        int[]partida=new int[] {1, 2, 1, 1, 2, 2, 2, 1, 1,8,3,2,4,9,1,5,6,7};
       for(int i=0;i<9;i++){
           if(partida[9+i]==1){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img1);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 1 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img1);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 1 equis");
               }
           }else if(partida[9+i]==2){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img2);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 2 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img2);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 2 equis");
               }
           }else if(partida[9+i]==3){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img3);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 3 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img3);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 3 equis");
               }
           }else if(partida[9+i]==4){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img4);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 4 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img4);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 4 equis");
               }
           }else if(partida[9+i]==5){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img5);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 5 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img5);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 5 equis");
               }
           }else if(partida[9+i]==6){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img6);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 6 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img6);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 6 equis");
               }
           }else if(partida[9+i]==7){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img7);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 7 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img7);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 7 equis");
               }
           }else if(partida[9+i]==8){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img8);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 8 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img8);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 8 equis");
               }
           }else if(partida[9+i]==9){
               if(partida[i]==1) {
                   ImageView img = (ImageView) findViewById(R.id.img9);
                   img.setImageResource(R.drawable.circulo);
                   System.out.println("posicion 9 circulo");
               }else{
                   ImageView img = (ImageView) findViewById(R.id.img9);
                   img.setImageResource(R.drawable.equis);
                   System.out.println("posicion 9 equis");
               }
           }
           Handler handler = new Handler();
           handler.postDelayed(new Runnable() {
               public void run() {
                   c++;
               }
           }, 10);

       }
    }*/




    //para guardar partidas en base e datos de hostinger
    //envío de parámetros mediante post
   public void cargar() {
        //iniciar tarea en segundo plano
        ComunicacionTask com = new ComunicacionTask();
        //le pasa como parámetro la dirección
        //de la página
       String parametro="nick="+nick+"&partida="+partida+"&fecha="+fecha+"&resultado="+resultado;
        com.execute("http://davidgarrido.hol.es/partidas.php", parametro);
    }



    private class ComunicacionTask extends AsyncTask<String, Void, String> {

        //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {

            String cadenaJson = "";
            try {
                //monta la url con la dirección y parámetro
                //de envío
                System.out.println("la url es:" + params[0] + "?" + params[1]);
                URL url = new URL(params[0] + "?" + params[1]);
                URLConnection con = url.openConnection();
                //recuperacion de la respuesta JSON
                String s;
                InputStream is = con.getInputStream();
                //utilizamos UTF-8 para que interprete
                //correctamente las ñ y acentos
                BufferedReader bf = new BufferedReader(
                        new InputStreamReader(is, Charset.forName("UTF-8")));
                while ((s = bf.readLine()) != null) {
                    cadenaJson += s;

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return cadenaJson;
        }

        /*@Override
        protected void onPostExecute(String result) {
            String[] datos = null;
            try {
                //creamos un array JSON a partir de la cadena recibida
                JSONArray jarray = new JSONArray("" + result);
                //creamos el array de String con el tamaño
                //del array JSON
                datos = new String[jarray.length()];
                String suma = "";
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject job = jarray.getJSONObject(i);
                    System.out.println("FILA: " + job.toString());
                    //tvPagina.setText("FILA: "+job.toString());

                    suma = suma + "El titulo es: " + job.get("titulo") + "\n" + "El autor es: " + job.get("autor") + "\n" + "El número de páginas es: " + job.get("paginas");
               System.out.println("las partidas guardadas en hostinger son:"+suma);
                }


            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }*/
    }

    public String obtenerFecha(){
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
        String annio = Integer.toString(c.get(Calendar.YEAR));
        String hora;
        String minuto;
        String segundo;

        if(c.get(Calendar.SECOND)<10){
            segundo="0"+c.get(Calendar.SECOND);
        }else{
            segundo=Integer.toString(c.get(Calendar.SECOND));
        }
        if(c.get(Calendar.MINUTE)<10){
            minuto="0"+c.get(Calendar.MINUTE);
        }else{
            minuto=Integer.toString(c.get(Calendar.MINUTE));
        }
        if(c.get(Calendar.HOUR_OF_DAY)<10){
            hora="0"+c.get(Calendar.HOUR_OF_DAY);
        }else{
            hora=Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        }



        String fecha=dia+"/"+mes+"/"+annio+"Hora:"+hora+":"+minuto+":"+segundo;
        return fecha;
    }

}




