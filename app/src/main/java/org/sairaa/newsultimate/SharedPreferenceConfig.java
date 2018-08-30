package org.sairaa.newsultimate;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;
    private int tab, sports, politics;

    public SharedPreferenceConfig(Context context){
        this.context = context;
        tab = 1;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_360), Context.MODE_PRIVATE);

    }
    public void writeNoOfTabs(){
        if(readSportsStatus()){
            sports = 1;
        }else
            sports = 0;

        if(readPoliticsStatus()){
            politics = 1;
        }else
            politics = 0;

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(context.getResources().getString(R.string.noOfTabs_preference), tab+sports+politics);
        Log.i("SharedWriteTab: ",""+tab+sports+politics);
        editor.commit();
    }

    public int readNoOfTabs(){
        int tabs = 1;
        tabs = sharedPreferences.getInt(context.getResources().getString(R.string.noOfTabs_preference),tabs);
        Log.i("SharedReadTab: ",""+tabs);
        return tabs;
    }

    public void writeSportsStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.sports_status_preference), status);
        Log.i("SharedeWriteSports: ",""+status);
        editor.commit();
    }

    public boolean readSportsStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.sports_status_preference),false);
        Log.i("SharedReadSports: ",""+status);
        return status;
    }
    public void writePoliticsStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.politics_status_preference), status);
        Log.i("SharedWritePolitics ",""+status);
        editor.commit();
    }

    public boolean readPoliticsStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.politics_status_preference),false);
        Log.i("SharedreadPolitics: ",""+status);
        return status;
    }
    public void writeFirstTimeNews(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.first_time_status_preference), status);
        Log.i("SharedWritePolitics ",""+status);
        editor.commit();
    }
    public boolean readFirstTimeNews(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.first_time_status_preference),false);
        Log.i("SharedreadPolitics: ",""+status);
        return status;
    }
}
