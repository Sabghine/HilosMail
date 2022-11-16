package com.example.hilosmail;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Mail;
import com.example.hilosmail.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MessageFragment extends Fragment {
    private AppDataBase database ;
    Button button;
    EditText to;
    EditText subject;
    EditText message;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        button = (Button) view.findViewById(R.id.btnSend);
        to = view.findViewById(R.id.txtTo);
        subject = view.findViewById(R.id.txtSub);
        message = view.findViewById(R.id.txtMsg);
        database = AppDataBase.getAppDatabase(getContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!to.getText().toString().isEmpty() && !subject.getText().toString().isEmpty() && !message.getText().toString().isEmpty() )
                {
                    if(EmailisValid(to.getText().toString()))
                        {
                    String email = sharedpreferences.getString("email", null);
                    List<User> users = database.userDao().getAll();
                    User connected = database.userDao().getUserByEmail(email);
                    List<User> usersexceptconnected = users;
                    usersexceptconnected.remove(connected);
                    if (to.getText().toString().equals(email)) {
                        showmessage("you cant send an email to yourself !", v);

                    }
                    else
                            {
                        for (User u : usersexceptconnected)
                        {
                            if (to.getText().toString().equals(u.getEmail()))
                            {
                                Mail m = new Mail(subject.getText().toString(),message.getText().toString(),email,to.getText().toString(),new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                                String cat = database.mailDao().getcategoryofsenderforreceiver(email,to.getText().toString());
                                m.setCategory(cat);
                                database.mailDao().insertOne(m);
                                showmessage("email sent !", v);
                                navigatetoinbox();

                            }
                        }
                                showmessage("User email doesnt exist !", v);
                            }

                        }
                    else
                    {
                        showmessage("please type a valid email address !", v);
                    }
                }
                else
                {
                    if(to.getText().toString().isEmpty())
                    {
                        showmessage("please specify your receiver address",v);
                    }
                    else if(subject.getText().toString().isEmpty())
                    {
                        showmessage("please specify your email subject",v);
                    }
                    else
                    {
                        showmessage("please specify you mail content",v);
                    }
                }
            }
        });
        return view;
    }
    public void showmessage(String msg,View v)
    {
        Context context = v.getContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public boolean EmailisValid(String email) {
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void navigatetoinbox()
    {
        Intent i = new Intent(getActivity(), FragmentSupportActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }
}