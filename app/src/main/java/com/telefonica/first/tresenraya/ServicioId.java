package com.telefonica.first.tresenraya;

/**
 * Created by David Garrido on 03/04/2017.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by telefonica on 29/03/2017.
 */

public class ServicioId extends Service {
    static Timer t;
    public static int  miId = 0;
    public static int idBase=0;
    public static int contador=0;
    public ServicioId() {
        t = new Timer();

    }
    public void onDestroy() {
        super.onDestroy();
        t.cancel();
        Toast.makeText(this, "destruyendo servicio", Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

        public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context,Principal.class);
        context.startService(service);

        }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "se ha iniciado el servicio", Toast.LENGTH_LONG).show();
        iniciarTemporizador();
        return Service.START_STICKY;
    }

    void iniciarTemporizador() {

        System.out.println("En el temporizador");
        t = new Timer();
        //inicia el temporizador de manera inmediata
        // con una repetición de 0.5 segundos
        t.schedule(new MiTimer(), 0, 3000);

    }





    private class MiTimer extends TimerTask {

        @Override
        public void run() {
            System.out.println("En RUN");
            ServicioId.CalculoTask com = new ServicioId.CalculoTask();
            //le pasa como parámetro la dirección
            //de la página
            com.execute("http://davidgarrido.hol.es/id.php");

        }
    }

    public class CalculoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String cadenaJson = "";
            try {
                //monta la url con la dirección y parámetro
                //de envío
                System.out.println("la url es:" + params[0]);
                URL url = new URL(params[0]);
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
                    System.out.println("el json id es :" + cadenaJson);
                    }


            } catch (IOException ex) {

                ex.printStackTrace();
            }

            return cadenaJson;
        }
        @Override
        protected void onPostExecute(String result) {

            String[] datos = null;

            JSONArray jarray = null;
            try {
                jarray = new JSONArray("" + result);
                datos = new String[jarray.length()];
                String suma = "";
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject job = jarray.getJSONObject(i);
                    System.out.println("FILA: " + job.toString());
                    String jugador= (String) job.get("nick");
                    String id=(String) job.get("id");
                    idBase=Integer.parseInt(id);
                    String fecha=(String) job.get("fecha");
                    String partida= (String) job.get("partida");
                    String resultado=(String) job.get("resultado");
                    System.out.println("el resultado de la peticiön Json es: "+"jugador:"+jugador+" el id: "+idBase+" la fecha es: "+fecha+" partida: "+partida);
                    if (contador==0){
                        miId= idBase;
                        System.out.println("miID es :" + miId);
                        contador++;

                    }else{
                        contador++;
                        if (idBase > miId) {
                            if(resultado.equalsIgnoreCase("ganado")){
                                String mensaje= "ESPABILA!!! "+"\n"+jugador.toUpperCase()+" HA "+resultado.toUpperCase()+" SU PARTIDA "+"\n"+"FECHA "+fecha.toUpperCase();
                                Toast.makeText(getBaseContext(),mensaje, Toast.LENGTH_LONG).show();
                            }else{
                                String mensaje= "☺☺☺☺☺☺☺ : "+"\n"+jugador.toUpperCase()+" HA "+resultado.toUpperCase()+" SU PARTIDA "+"\n"+"FECHA "+fecha.toUpperCase();
                                Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_LONG).show();
                            }

                            miId = idBase;
                        }
                    }
            }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
           /* String cadenaJson= result;
            String[] lineaSeparada=cadenaJson.substring(cadenaJson.indexOf("{")+1,cadenaJson.indexOf("}")).split(":");
            System.out.println("on result el json id es :" + cadenaJson);
            System.out.println("miID es :" + miId);
            if (contador==0){
                miId=  Integer.parseInt(lineaSeparada[1].substring(1,lineaSeparada[1].length()-1));
                System.out.println("miID es :" + miId);
                contador++;

            }else{

                idBase=Integer.parseInt(lineaSeparada[1].substring(1,lineaSeparada[1].length()-1));
                if(idBase>miId){
                    Toast.makeText(getBaseContext(), "un jugador ha ganado "+	result, Toast.LENGTH_LONG).show();
                    miId=idBase;
                }

            }*/

        }
        }

}





