package com.szcomtop.meal.activity;

import android.content.Intent;
import android.os.Bundle;

import com.szcomtop.meal.R;

/**
 * Created by wuming on 16/11/15.
 */
public class WelcomeActivity  extends  BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.welcome_iv).postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        },500);
    }

}
