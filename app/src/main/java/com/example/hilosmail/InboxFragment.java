package com.example.hilosmail;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Mail;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment implements MyAdapter.OnNoteListener {
    private List<Mail> mails=new ArrayList<>();
    private AppDataBase database;
    SharedPreferences sharedpreferences;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_inbox, container, false);
        database = AppDataBase.getAppDatabase(getContext());
        sharedpreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        mails=database.mailDao().getMailByReceiver(sharedpreferences.getString("email", null));
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(new MyAdapter(getContext(),mails,this));
        return v;
    }

    @Override
    public void OnNoteClick(int position) {
        View view = recyclerView.getLayoutManager().findViewByPosition(position);
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
        bundle.putString("sendingemail",emailaddress.getText().toString());
        bundle.putString("sendingdate",sendingdate.getText().toString());
        MailDetailsFragment fragment = new MailDetailsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


        //Toast toast = Toast.makeText(getContext(), textView.getText().toString(),Toast.LENGTH_SHORT);
        //toast.show();

    }

}
