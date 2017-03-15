package sg.edu.nus.iss.medipal.manager;

/**
 * Created by issuser on 15/3/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by : Navi on 04-03-2017.
 * Description : This is the container for shared preferences
 * Modified by :
 * Reason for modification :
 */
public class PreferenceManager {

    private Context context;

    SharedPreferences sharedPref;
    Editor preferenceEditor;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "medipalStoredPreferences";


    public PreferenceManager(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        preferenceEditor = sharedPref.edit();
    }

}
