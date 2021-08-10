package com.arbud.validasilogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    public static final String FILENAME = "Login";
    EditText editUsername, editPassword, editEmail, editNamaLengkap, editAsalSekolah, editAlamat;
    Button btnSimpan;
    TextView textViewPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Halaman Depan");

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        editNamaLengkap = findViewById(R.id.editNamaLengkap);
        editAsalSekolah = findViewById(R.id.editAsalSekolah);
        editAlamat = findViewById(R.id.editAlamatTinggal);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setVisibility(View.GONE);
        editUsername.setEnabled(false);
        editPassword.setVisibility(View.GONE);
        textViewPassword = findViewById(R.id.textViewPassword);
        editEmail.setEnabled(false);
        editNamaLengkap.setEnabled(false);
        editAsalSekolah.setEnabled(false);
        editAlamat.setEnabled(false);
        bacaFileLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulayout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_metu:
                tampilkanDialogKonfirmasiLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void bacaFileLogin() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

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
            } catch (IOException e ) {
                System.out.println("Error" + e.getMessage());
            }
            String data = text.toString();
            String[] datauser = data.split(";");
            bacaDataUser(datauser[0]);
        }
    }

    void bacaDataUser(String fileName) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, fileName);
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

            editUsername.setText(datauser[0]);
            editEmail.setText(datauser[2]);
            editNamaLengkap.setText(datauser[3]);
            editAsalSekolah.setText(datauser[4]);
            editAlamat.setText(datauser[5]);
        } else {
            Toast.makeText(this, "User Tidak Ditemukan",Toast.LENGTH_SHORT).show();
        }
    }

    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    void tampilkanDialogKonfirmasiLogout() {
        new AlertDialog.Builder(this).setTitle("Logout").setMessage("Apakah Anda Yakin Ingin Logout").
                setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hapusFile();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No",null).show();
    }
}
