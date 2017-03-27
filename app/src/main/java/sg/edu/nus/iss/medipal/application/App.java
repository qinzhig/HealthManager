package sg.edu.nus.iss.medipal.application;

import android.app.Application;

import sg.edu.nus.iss.medipal.manager.HealthManager;

/**
 * Created by zhiguo on 15/3/17.
 */

public class App extends Application {

    public static HealthManager hm;

    @Override
    public void onCreate() {
        super.onCreate();
        hm = new HealthManager();
    }
}
