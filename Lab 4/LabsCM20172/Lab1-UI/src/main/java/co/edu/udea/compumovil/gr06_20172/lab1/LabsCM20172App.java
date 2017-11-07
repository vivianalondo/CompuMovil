package co.edu.udea.compumovil.gr06_20172.lab1;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by viviana on 6/11/17.
 */

public class LabsCM20172App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
