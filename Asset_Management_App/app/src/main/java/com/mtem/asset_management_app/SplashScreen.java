package com.mtem.asset_management_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends Activity {
    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
         try {
            super.onCreate(savedInstanceState);
            ConnectionClass connectionClass=new ConnectionClass();
            setContentView(R.layout.activity_splash_screen);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }catch (Exception e){
             Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
             e.printStackTrace();
        }
    }
}