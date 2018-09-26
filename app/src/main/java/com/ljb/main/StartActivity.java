package com.ljb.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.longjinbin.medcine.R;

public class StartActivity extends AppCompatActivity {


    private static final long SPLASH_DELAY_MILLIS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
                }
                }, SPLASH_DELAY_MILLIS);

}
    private void goHome() {
        Intent intent=null;
        intent = new Intent(StartActivity.this, MainActivity.class);
        StartActivity.this.startActivity(intent);
        StartActivity.this.finish();
}

}
