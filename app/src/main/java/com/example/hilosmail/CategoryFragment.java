package com.example.hilosmail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Category;

import java.util.List;


public class CategoryFragment extends Fragment  {
    EditText categoryname ;
    private AppDataBase database ;
    Button btncategory;
    TextView errorcategory;
    private DrawerLayout drawer;
    private CategoryFragment myContext;
    Toolbar toolbar;
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
         sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        categoryname=  view.findViewById(R.id.ed_category);
        btncategory =  view.findViewById(R.id.btn_addemail_tocategory);
        errorcategory= view.findViewById(R.id.tv_error_category);
        database = AppDataBase.getAppDatabase(getContext());
        drawer = view.findViewById(R.id.drawer_layout);
        btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 toolbar = view.findViewById(R.id.toolbar);
                String catname= categoryname.getText().toString();

                if (isCategoryvalid(catname)) {
                      errorcategory.setVisibility(errorcategory.INVISIBLE);
                      Category c = new Category(categoryname.getText().toString(),sharedpreferences.getInt("userid",-1));
                      database.categoryDao().insertOne(c);
                    //drawer = view.findViewById(R.id.drawer_layout);
                    //NavigationView navigationView = v.findViewById(R.id.nav_view);
                    refreshNavigationView();
                      Toast toast = Toast.makeText(getContext(), "Category added with success", Toast.LENGTH_SHORT);
                      toast.show();
                  }
                 else
                 {
                     //Toast.makeText(getContext(), "either categoy exist or u didnt type anything", Toast.LENGTH_SHORT).show();
                     errorcategory.setVisibility(errorcategory.VISIBLE) ;
                 }
            }
        });



        return view;
    }
    public boolean isString(String catname) {
        return catname.matches("[a-zA-Z]+");
    }
    public boolean isCategoryvalid(String catname) {

        if(catname.isEmpty() || !isString(catname) ){
            return false;
        }
        List<Category> categories = database.categoryDao().getAll();
        for(Category c : categories)
        {
            if (c.getName().equals(catname) && c.getUserid()==sharedpreferences.getInt("userid",-1))
            {
                return false;
            }
        }
        return true ;
    }

    public  void refreshNavigationView(){
        Intent i = new Intent(getActivity(), FragmentSupportActivity.class);
        startActivity(i);
    }
}