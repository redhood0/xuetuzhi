package com.redhood.xtz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.redhood.xtz.R;
import com.redhood.xtz.util.StatusUtil;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private ImageView iv_login_delete_username;
    private ImageView iv_login_password_eye;
    private EditText et_login_usename;
    private EditText ed_login_password;
    private boolean isShowPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        StatusUtil.makeStatusBarTransparent(this);

        btn_login = findViewById(R.id.btn_login);
        iv_login_delete_username = findViewById(R.id.iv_login_delete_username);
        et_login_usename = findViewById(R.id.et_login_usename);
        iv_login_password_eye = findViewById(R.id.iv_login_password_eye);
        ed_login_password = findViewById(R.id.ed_login_password);

        btn_login.setOnClickListener(v -> {
            if (loginCheck()) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_login_delete_username.setOnClickListener(v -> {
            et_login_usename.setText("");
        });
        iv_login_password_eye.setOnClickListener(v -> {
            if (isShowPassword) {
                iv_login_password_eye.setImageResource(R.mipmap.login_eye_close);
                ed_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isShowPassword = false;
            } else if (!isShowPassword) {
                iv_login_password_eye.setImageResource(R.mipmap.login_eye_open);
                ed_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isShowPassword = true;
            }
        });
    }

    private boolean loginCheck() {
        return true;
    }

}
