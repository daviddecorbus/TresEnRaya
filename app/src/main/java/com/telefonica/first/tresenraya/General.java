package com.telefonica.first.tresenraya;

import android.app.Activity;
import android.content.Intent;
/**
 * Created by David Garrido on 27/03/2017.
 */

public class General {
    public static boolean menu(Activity c, int id) {
        if (id == R.id.action_Principal) {
            setActivity(c, Principal.class);
            return true;
        } else if (id == R.id.action_Jugar) {
            setActivity(c, Jugar.class);
            return true;
        } else if (id == R.id.action_Configuracion) {
            setActivity(c, Configuracion.class);
            return true;
        }
        return false;
    }

    static void setActivity(Activity c, Class cl){
        Intent intent=new Intent(c,cl);
        c.startActivity(intent);

    }
}
