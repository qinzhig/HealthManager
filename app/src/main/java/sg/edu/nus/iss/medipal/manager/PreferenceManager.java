package sg.edu.nus.iss.medipal.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by : Navi on 04-03-2017.
 * Description : This is the container for shared preferences
 * Modified by : Divahar on 3/21/2017
 * Reason for modification : Added shared preference for help screens
 */
public class PreferenceManager {

    private Context context;

    SharedPreferences sharedPref;
    Editor preferenceEditor;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "medipalStoredPreferences";

    /*
    constructor
    */
    public PreferenceManager(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        preferenceEditor = sharedPref.edit();
    }

    public void setSplashScreenPref(String displayAtStart) {
        preferenceEditor.putString("SPLASH_SCREEN", displayAtStart);
        preferenceEditor.commit();
    }

    public String getSplashScreenPref() {
        return sharedPref.getString("SPLASH_SCREEN", null);
    }

    public void setFirstTimeFlag(String firstTime) {
        preferenceEditor.putString("FIRST_TIME", firstTime);
        preferenceEditor.commit();
    }

    public String getFirstTimeFlag() {
        return sharedPref.getString("FIRST_TIME", null);
    }


    public void storeAppointmentInfo(String appointmentId, String appointmentInfo) {
        //Store the notification Id and title as key-value pair
        preferenceEditor.putString(appointmentId, appointmentInfo);
        // commit changes
        preferenceEditor.commit();
    }

    public void storeConsumptionInfo(String key, int value){
        preferenceEditor.putInt(key,value);
        preferenceEditor.commit();
    }

    public int getConsumptionInfo(String key){
        return sharedPref.getInt(key,0);
    }

    public String getAppointmentInfo(String appointmentId) {
        return sharedPref.getString(appointmentId, null);
    }

    public void deleteAppointmentInfo(String appointmentId) {
        //Remove the appointment details stored using the Id
        preferenceEditor.remove(appointmentId);
        // commit changes
        preferenceEditor.commit();
    }

}
