package com.example.hilosmail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.User;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private AppDataBase database ;
    EditText fnameed;
    EditText lnameed;
    EditText emailed;
    EditText passworded;
    TextView fnerror;
    TextView lnerror;
    TextView emailerror;
    TextView passworderror;
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fnameed  = findViewById(R.id.et_first_name);
        lnameed  = findViewById(R.id.et_last_name);
        emailed  = findViewById(R.id.et_email);
        passworded  = findViewById(R.id.et_password);
        fnerror =  findViewById(R.id.tv_error_first_name);
        lnerror =  findViewById(R.id.tv_error_last_name);
        emailerror =  findViewById(R.id.tv_error_email);
        passworderror =  findViewById(R.id.tv_error_password);
        button =findViewById(R.id.btn_register);
        database = AppDataBase.getAppDatabase(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = fnameed.getText().toString();
                String lastname = lnameed.getText().toString();
                String email    =emailed.getText().toString();
                String password = passworded.getText().toString();
                if(!fnameisvalid() && !lnameisvalid() && !EmailisValid(email) && !passwordisvalid())
                {
                    showerror(fnerror);
                    showerror(lnerror);
                    showerror(passworderror);
                    showerror(emailerror);
                }
                if(fnameisvalid() && !lnameisvalid() && !EmailisValid(email) && !passwordisvalid())
                {
                    hideerror(fnerror);
                    showerror(lnerror);
                    showerror(passworderror);
                    showerror(emailerror);
                }
                if(!fnameisvalid() && lnameisvalid() && !EmailisValid(email) && !passwordisvalid())
                {
                    showerror(fnerror);
                    hideerror(lnerror);
                    showerror(passworderror);
                    showerror(emailerror);
                }
                if(!fnameisvalid() && !lnameisvalid() && EmailisValid(email) && !passwordisvalid())
                {
                    showerror(fnerror);
                    showerror(lnerror);
                    showerror(passworderror);
                    hideerror(emailerror);
                }
                if(!fnameisvalid() && !lnameisvalid() && !EmailisValid(email) && passwordisvalid())
                {
                    showerror(fnerror);
                    showerror(lnerror);
                    hideerror(passworderror);
                    showerror(emailerror);
                }
                ///////////////
                if(!fnameisvalid() && !lnameisvalid() && EmailisValid(email) && passwordisvalid())
                {
                    showerror(fnerror);
                    showerror(lnerror);
                    hideerror(passworderror);
                    hideerror(emailerror);
                }
                if(fnameisvalid() && lnameisvalid() && !EmailisValid(email) && !passwordisvalid())
                {
                    hideerror(fnerror);
                    hideerror(lnerror);
                    showerror(passworderror);
                    showerror(emailerror);
                }
                ///////////////
                if(fnameisvalid() && lnameisvalid() && EmailisValid(email) && !passwordisvalid())
                {
                    hideerror(fnerror);
                    hideerror(lnerror);
                    showerror(passworderror);
                    hideerror(emailerror);
                }
                if(fnameisvalid() && lnameisvalid() && !EmailisValid(email) && passwordisvalid())
                {
                    hideerror(fnerror);
                    hideerror(lnerror);
                    hideerror(passworderror);
                    showerror(emailerror);
                }
                if(fnameisvalid() && !lnameisvalid() && EmailisValid(email) && passwordisvalid())
                {
                    hideerror(fnerror);
                    showerror(lnerror);
                    hideerror(passworderror);
                    hideerror(emailerror);
                }
                if(!fnameisvalid() && lnameisvalid() && EmailisValid(email) && passwordisvalid())
                {
                    showerror(fnerror);
                    hideerror(lnerror);
                    hideerror(passworderror);
                    hideerror(emailerror);
                }
                ///////////////
                if(fnameisvalid() &&lnameisvalid() &&EmailisValid(email) &&passwordisvalid())
                {
                    User user = new User(firstname,lastname,email,password);
                    database.userDao().insertOne(user);
                    Context context = getApplicationContext();
                    CharSequence text = "Account created successfully!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    gologin(v);
                }
            }
        });

    }

    public void gologin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public boolean fnameisvalid()
    {
        if(fnameed.getText().toString().isEmpty() || fnameed.getText().toString().length()<4) {

            return false;
        }
        //hideerror(fnerror);
        return true;
    }
    public boolean lnameisvalid()
    {
        if(lnameed.getText().toString().isEmpty() || lnameed.getText().toString().length()<4)
        {
//            showerror(lnerror);
            //lnerror.setVisibility(lnerror.VISIBLE);
            return false;
        }
  //      hideerror(lnerror);
        return true;
    }
    public boolean passwordisvalid()
    {
        if(passworded.getText().toString().isEmpty() || passworded.getText().toString().length()<8)
        {
    //        showerror(passworderror);
            //passworderror.setVisibility(passworderror.VISIBLE);
            return false;
        }
      //  hideerror(passworderror);
        return true;
    }
    public boolean EmailisValid(String email) {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || loginexist(email))
        {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && loginexist(email))
            {
                Toast.makeText(getApplicationContext(), "email already exist", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !loginexist(email))
            {
                return false;
            }
        }
        return true;

    }
    public void showerror(TextView error)
    {
        error.setVisibility(error.VISIBLE);
    }
    public void hideerror(TextView error) {error.setVisibility(error.INVISIBLE);}
    public boolean loginexist(String login)
    {
        List<User> users = database.userDao().getAll();
        for (User u : users)
        {
            if(u.getEmail().equals(login))
            {
                return true;
            }
        }
        return false;
    }
}
