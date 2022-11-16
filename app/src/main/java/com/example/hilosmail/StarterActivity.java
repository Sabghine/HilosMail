package com.example.hilosmail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StarterActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);


}
    public void gologin(View view) {
        Intent intent = new Intent(StarterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void goregsiter(View view) {
        Intent intent = new Intent(StarterActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
