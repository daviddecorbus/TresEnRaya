package com.telefonica.first.tresenraya;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import static com.telefonica.first.tresenraya.R.id.nick;

public class RankingActivity extends AppCompatActivity {
    TextView resultado;
    EditText nick;
    ListView listaPartidas;
    ArrayList<String> resultados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultado=(TextView)this.findViewById(R.id.ranking);
        nick=(EditText) this.findViewById(R.id.nickPartidas);
        listaPartidas=(ListView)this.findViewById(R.id.lista);
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
    public void cargar2(View v){
        ComunicacionTask2 com = new ComunicacionTask2();
        String parametro=nick.getText().toString().trim();
        com.execute("http://davidgarrido.hol.es/peticionPartida.php", parametro);
    }

    public class ComunicacionTask2 extends AsyncTask<String, Void, String>{

        //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... params) {

            String cadenaJson = "";
            try {
                //monta la url con la dirección y parámetro
                //de envío
                System.out.println("la url es:" + params[0] + "?nick=" + params[1]);
                URL url = new URL(params[0] + "?nick=" + params[1]);
                URLConnection con = url.openConnection();

                //recuperacion de la respuesta JSON
                String s;
                InputStream is = con.getInputStream();
                //utilizamos UTF-8 para que interprete
                //correctamente las ñ y acentos
                BufferedReader bf = new BufferedReader(new InputStreamReader(is, Charset.forName("utf-8")));

                while ((s = bf.readLine()) != null) {
                    cadenaJson += s;
                    System.out.println("las partidas guardadas en hostinger son:"+cadenaJson);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return cadenaJson;
        }

     @Override
        protected void onPostExecute(String result) {
         ArrayList<String> jason = new ArrayList<>();
          resultados = new ArrayList<>();
            String[] datos = null;
            try {
                //creamos un array JSON a partir de la cadena recibida
                JSONArray jarray = new JSONArray("" + result);
                //creamos el array de String con el tamaño
                //del array JSON

                datos = new String[jarray.length()];
                String suma = "";
                int partida=0;
                for (int i = 0; i < jarray.length(); i++) {
                    partida++;
                    JSONObject job = jarray.getJSONObject(i);

                    jason.add(job.get("nick") + "\n" +"****************"+ "\n" +"Partida "+partida+":"+"\n"+  "Partida: " + job.get("partida") + "\n" + "fecha: " + job.get("fecha"));
                    resultados.add((String) job.get("resultado"));
                    //tvPagina.setText("FILA: "+job.toString());
                    /*if(i==0){
                        suma = suma + "Jugador: " + job.get("nick") + "\n" +"****************"+ "\n" +"Partida "+partida+":"+"\n"+  "Partida: " + job.get("partida") + "\n" + "fecha: " + job.get("fecha")+"\n" +"---------------------------------"+"\n";
                    }else {
                        suma = suma + "Partida " + partida+ ":" + "\n" + "Partida: " + job.get("partida") + "\n" + "fecha: " + job.get("fecha") + "\n" + "---------------------------------" + "\n";
                        System.out.println("las partidas guardadas en hostinger son:" + suma);
                    }
                    resultado.setText(suma);
                    //para darle movimiento vertical tipo scroll al textview
                    resultado.setMovementMethod(new ScrollingMovementMethod());*/
                }


            } catch (JSONException ex) {
                ex.printStackTrace();
            }
         ArrayAdapter<String> adapter= new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, jason);

         listaPartidas.setAdapter(adapter);
         listaPartidas.setOnItemClickListener(
                 new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Toast.makeText(getBaseContext(), "Partida: "+resultados.get(position), Toast.LENGTH_LONG).show();
                     }

                 });
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
