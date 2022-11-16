package com.example.hilosmail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private AppDataBase database ;
    Button login;
    EditText email;
    EditText password;
    TextView erroremail;
    TextView errorpassword;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = AppDataBase.getAppDatabase(this);
        TextView register =(TextView) findViewById(R.id.tv_to_register);
        login = findViewById(R.id.btn_login);
        email=findViewById(R.id.et_email);
        password= findViewById(R.id.et_password);
        erroremail=findViewById(R.id.tv_error_email);
        errorpassword=findViewById(R.id.tv_error_password);
        hideerror(erroremail);
        hideerror(errorpassword);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    if(!email.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "please fill the password field", Toast.LENGTH_SHORT).show();
                    }
                    else if(email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "please fill the email field", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), " please fill the all fields", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                hideerror(erroremail);
                hideerror(errorpassword);
                boolean loginexist =  loginexist(email.getText().toString());
                boolean passwordexist =  passwordexist(password.getText().toString());
                if(loginexist && passwordexist)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Logged in successfully!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("email",email.getText().toString());
                    User current=database.userDao().getUserByEmail(email.getText().toString());
                    editor.putInt("userid",current.getId());
                    editor.commit();
                    enterapplication(v);
                }
                else
                {
                    if(!loginexist || !passwordexist)
                    {
                        if(!loginexist && passwordexist)
                        {
                                showerror(erroremail);
                                hideerror(errorpassword);
                        }
                        else if(loginexist && !passwordexist)
                        {
                            showerror(errorpassword);
                            hideerror(erroremail);
                        }
                        else
                        {
                            showerror(erroremail);
                            showerror(errorpassword);
                        }
                    }
                }
            }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goRegister();
            }
        });
    }
    public void goRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void showerror(TextView error)
    {
        error.setVisibility(error.VISIBLE);
    }
    public void hideerror(TextView error) {error.setVisibility(error.INVISIBLE);}

    public void enterapplication(View view) {
        Intent intent = new Intent(LoginActivity.this, FragmentSupportActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
    /*public boolean Userexists(String login,String password)
    {
        User  user = database.userDao().getUser(login,password);
        if(user !=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/
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
    public boolean passwordexist(String password)
    {
        List<User> users = database.userDao().getAll();
        for (User u : users)
        {
            if(u.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }

}
