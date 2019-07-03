package com.redhood.xtz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.redhood.xtz.R;
import com.redhood.xtz.util.StatusUtil;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        StatusUtil.makeStatusBarTransparent(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v ->{
            if(loginCheck()){
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean loginCheck(){
       return true;
    }

}
