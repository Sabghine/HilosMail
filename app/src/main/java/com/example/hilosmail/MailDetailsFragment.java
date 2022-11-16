package com.example.hilosmail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Category;

import java.util.ArrayList;
import java.util.List;


public class MailDetailsFragment extends Fragment {
    private AppDataBase database;
    SharedPreferences sharedpreferences;
    private List<Category> categories;
    Spinner dropdown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedpreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        database = AppDataBase.getAppDatabase(getContext());
        View v=  inflater.inflate(R.layout.fragment_mail_details,container, false);
        Bundle bundle = this.getArguments();
        int access = bundle.getInt("access");
        if(access ==1)
        {
            v.findViewById(R.id.btn_addemail_tocategory).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.list_categories).setVisibility(View.INVISIBLE);
        }
        Button b = v.findViewById(R.id.btn_addemail_tocategory);
        TextView object = v.findViewById(R.id.tv_object);
        TextView senderemail = v.findViewById(R.id.tv_sendername);
        TextView message = v.findViewById(R.id.tv_allmsg);
        ImageView img = v.findViewById(R.id.hellothere);
        img.setBackgroundResource(R.drawable.ic_mail_profile_image_send_receive);
        object.setText(bundle.getString("mailtitle"));
        message.setText(bundle.getString("content"));
        senderemail.setText(bundle.getString("sendingemail"));
        dropdown = v.findViewById(R.id.list_categories);
        categories = database.categoryDao().getCategoryByUser(sharedpreferences.getInt("userid",-1));
        List<String> categories_names = new ArrayList<>();
        for (Category c : categories)
        {
            categories_names.add(c.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories_names);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dropdown.setAdapter(adapter);






        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Spinner spinner = (Spinner)v.findViewById(R.id.list_categories);
                String categoryselected = dropdown.getSelectedItem().toString();
                String sendermail = senderemail.getText().toString();
                //Toast.makeText(getContext(), categoryselected+sendermail, Toast.LENGTH_SHORT).show();
                database.mailDao().UpdateMailsCategory(categoryselected,sendermail);
                Toast.makeText(getContext(), "email added to category "+categoryselected, Toast.LENGTH_SHORT).show();
                //navigatetoinbox();
            }
        });
          return v;
    }
    public void navigatetoinbox()
    {
        Intent i = new Intent(getActivity(), InboxFragment.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }
}