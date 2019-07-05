package com.redhood.xtz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.redhood.xtz.R;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    private Intent intent;
    private ImageView iv_welcomelogo;
    private TextView iv_welcomeword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        iv_welcomelogo = findViewById(R.id.iv_welcomelogo);
        iv_welcomeword = findViewById(R.id.iv_welcomeword);
        iv_welcomelogo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.welcome_logo));
        iv_welcomeword.setAnimation(AnimationUtils.loadAnimation(this, R.anim.welcome_logo));
        handler.sendEmptyMessageDelayed(0, 1000);
        //setContentView(R.layout.activity_main);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    };
}
