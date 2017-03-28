package com.telefonica.first.tresenraya;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Jugar extends AppCompatActivity {
    public int[] tablero = new int [9];
    public boolean jugar=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        for (int i=0; i<9; i++){
            tablero[i]=0;
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
             if((tablero[0]==-1&&tablero[1]==-1&&tablero[2]==-1) ||
                    (tablero[3]==-1&&tablero[4]==-1&&tablero[5]==-1) ||
                    (tablero[6]==-1&&tablero[7]==-1&&tablero[8]==-1) ||
                    (tablero[0]==-1&&tablero[3]==-1&&tablero[6]==-1) ||
                    (tablero[1]==-1&&tablero[4]==-1&&tablero[7]==-1) ||
                    (tablero[2]==-1&&tablero[5]==-1&&tablero[8]==-1) ||
                    (tablero[0]==-1&&tablero[4]==-1&&tablero[8]==-1) ||
                    (tablero[2]==-1&&tablero[4]==-1&&tablero[6]==-1)) {

                return true;
            }
         return false;
    }
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
            TextView mostrarAgenda = (TextView) findViewById(R.id.mensajesSistema);
            mostrarAgenda.setText("JUGADOR GANA!!");
            jugar=false;
            return true;
        }
        return false;
    }
    public boolean checkWinRobot1(){
        if((tablero[0]==-1&&tablero[1]==-1&&tablero[2]==-1) ||
                (tablero[3]==-1&&tablero[4]==-1&&tablero[5]==-1) ||
                (tablero[6]==-1&&tablero[7]==-1&&tablero[8]==-1) ||
                (tablero[0]==-1&&tablero[3]==-1&&tablero[6]==-1) ||
                (tablero[1]==-1&&tablero[4]==-1&&tablero[7]==-1) ||
                (tablero[2]==-1&&tablero[5]==-1&&tablero[8]==-1) ||
                (tablero[0]==-1&&tablero[4]==-1&&tablero[8]==-1) ||
                (tablero[2]==-1&&tablero[4]==-1&&tablero[6]==-1)) {
            TextView mostrarAgenda = (TextView) findViewById(R.id.mensajesSistema);
            mostrarAgenda.setText("ROBOT GANA!!");
            jugar=false;
            return true;
        }
        return false;
    }

  public void reinicio(View v){
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
      TextView mostrarAgenda = (TextView) findViewById(R.id.mensajesSistema);
      mostrarAgenda.setText("");
      jugar=true;
   }

    public void jugador1(View v) {
        if (jugar == true) {
            if (tablero[0] == 0) {
                tablero[0] = 1;
                ImageView img = (ImageView) findViewById(R.id.img1);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador2(View v){
        if (jugar == true) {
            if (tablero[1] == 0) {
                tablero[1] = 1;
                ImageView img = (ImageView) findViewById(R.id.img2);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador3(View v){
        if (jugar == true) {
            if (tablero[2] == 0) {
                tablero[2] = 1;
                ImageView img = (ImageView) findViewById(R.id.img3);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador4(View v){
        if (jugar == true) {
            if (tablero[3] == 0) {
                tablero[3] = 1;
                ImageView img = (ImageView) findViewById(R.id.img4);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador5(View v){
        if (jugar == true) {
            if (tablero[4] == 0) {
                tablero[4] = 1;
                ImageView img = (ImageView) findViewById(R.id.img5);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador6(View v){
        if (jugar == true) {
            if (tablero[5] == 0) {
                tablero[5] = 1;
                ImageView img = (ImageView) findViewById(R.id.img6);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador7(View v){
        if (jugar == true) {
            if (tablero[6] == 0) {
                tablero[6] = 1;
                ImageView img = (ImageView) findViewById(R.id.img7);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador8(View v){
        if (jugar == true) {
            if (tablero[7] == 0) {
                tablero[7] = 1;
                ImageView img = (ImageView) findViewById(R.id.img8);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
    public void jugador9(View v){
        if (jugar == true) {
            if (tablero[8] == 0) {
                tablero[8] = 1;
                ImageView img = (ImageView) findViewById(R.id.img9);
                img.setImageResource(R.drawable.equis);
                checkWinPlayer1();
                pensarRobot();
            }
        }
    }
public boolean pensarRobot() {
    if (jugar == true) {
        for (int i = 0; i < 9; i++) {
            if (tablero[i] == 0) {
                tablero[i] = -1;
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
            jugarRobot(4);
            return true;
        }
        for (int i = 0; i < 9; i++) {
            if (tablero[i] == 0) {
                jugarRobot(i);
                return true;
            }
        }


    }

    return true;
}

public void jugarRobot(int i){
    tablero[i]=-1;
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



}
