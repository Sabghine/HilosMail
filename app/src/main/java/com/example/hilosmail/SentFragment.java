package com.example.hilosmail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Mail;

import java.util.ArrayList;
import java.util.List;

public class SentFragment extends Fragment implements MyAdapter.OnNoteListener {

    private List<Mail> mails=new ArrayList<>();
    private AppDataBase database;
    SharedPreferences sharedpreferences;
    RecyclerView recyclerView2;
    String categoryname;
    Button deletecat;
    int visibility;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_sent, container, false);
        categoryname = getCategoryname();
        database = AppDataBase.getAppDatabase(getContext());
        sharedpreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        mails=getemails();
        recyclerView2 = v.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView2.setAdapter(new MyAdapter(getContext(),mails,this));
        deletecat = v.findViewById(R.id.deletecat);
        if(getVisibility()==1) {
         deletecat.setVisibility(View.INVISIBLE);
            return v;
        }
        deletecat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sender = database.mailDao().getsenderbycategory(categoryname);
                database.mailDao().deletecategoryfrommails(null,sharedpreferences.getString("email", null),sender);
                database.categoryDao().DeleteCategory(categoryname,sharedpreferences.getInt("userid",-1));
                Intent i = new Intent(getActivity(), FragmentSupportActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void OnNoteClick(int position) {
        View view = recyclerView2.getLayoutManager().findViewByPosition(position);
        TextView firstandlastname = view.findViewById(R.id.first_and_lastname);
        TextView item_mail_title = view.findViewById(R.id.item_mail_title);
        TextView item_mail_content = view.findViewById(R.id.item_mail_content);
        TextView emailaddress = view.findViewById(R.id.item_mail_address);
        TextView sendingdate = view.findViewById(R.id.item_mail_date);
        //Mail m = new Mail(item_mail_title.getText().toString(),item_mail_content.getText().toString(),emailaddress.getText().toString(),sharedpreferences.getString("email", null),sendingdate.getText().toString());
        Bundle bundle = new Bundle();
        //String personJsonString = Utils.getGsonParser().toJson(mail);
        bundle.putString("firstlastname",firstandlastname.getText().toString());
        bundle.putString("mailtitle",item_mail_title.getText().toString());
        bundle.putString("content",item_mail_content.getText().toString());
        bundle.putString("sendingemail","Me");
        bundle.putString("sendingdate",sendingdate.getText().toString());
        MailDetailsFragment fragment = new MailDetailsFragment();
        bundle.putInt("access",1);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
    public void setemails(List<Mail> emails)
    {
        this.mails = emails;
    }
    public List<Mail> getemails()
    {
        return this.mails;
    }
    public String getCategoryname()
    {
        return this.categoryname;
    }
    public void setCategoryname(String categoryname)
    {
        this.categoryname = categoryname;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}