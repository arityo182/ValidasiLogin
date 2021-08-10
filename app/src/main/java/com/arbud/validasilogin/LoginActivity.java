package com.arbud.validasilogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    public static final String FILENAME = "Login";

    EditText editUsername, editPassword;
    Button btnLogin, btnRegister;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.action_login);
        btnRegister = findViewById(R.id.action_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void checkUser() {
        String User = editUsername.getText().toString();
        String pass = editPassword.getText().toString();

        if (User.isEmpty()) {
            editUsername.setError("Masukan Username");
            editUsername.requestFocus();
        } else if (pass.isEmpty()) {
            editPassword.setError("Masukan Password");
            editPassword.requestFocus();
        } else {
            login();
        }
    }

    void login() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, editUsername.getText().toString());
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line !=null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Eror" + e.getMessage());
            }
            String data = text.toString();
            String[] datauser = data.split(";");

            if (datauser[1].equals(editPassword.getText().toString())) {
                simpanFileLogin();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Password Tidak Sesuai",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User Tidak Ditemukan",Toast.LENGTH_SHORT).show();
        }
    }

    void simpanFileLogin() {
        String isiFile = editUsername.getText().toString() + ";" + editPassword.getText().toString();
        File file = new File(getFilesDir(),FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file,false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
