package com.example.hilosmail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Category;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;

public class FragmentSupportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private AppDataBase database ;
    private List<Category> categories;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        database = AppDataBase.getAppDatabase(getApplicationContext());
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
           R.string.hilosMail_open,R.string.hilos_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InboxFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_inbox);}
        categories = database.categoryDao().getCategoryByUser(sharedpreferences.getInt("userid",-1));
        final Menu menu = navigationView.getMenu();
        for (Category c :categories) {
            MenuItem i = menu.add(R.id.group1, Menu.NONE, Menu.NONE, c.getName());
            i.setIcon(R.drawable.ic_special);
            i.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                    SentFragment sent = new SentFragment();
                    String connectedemail= sharedpreferences.getString("email", null);
                    String category = item.getTitle().toString();
                    sent.setCategoryname(category);
                    sent.setemails(database.mailDao().getMailByReceiverAndCategory(connectedemail,category));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sent).commit();
                    return false;
                }
            });
        }

    }


    public void showmessage(String msg, View v)
    {
        Context context = v.getContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        CharSequence text = "Log out before you go back";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment() ).commit();
                break;
            case R.id.nav_inbox:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InboxFragment() ).commit();
                break;
            case R.id.nav_logout:
                logout(getCurrentFocus());
                break;
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoryFragment()).commit();
                break;
            case R.id.nav_sent:
                SentFragment sentFragment = new SentFragment();
                sentFragment.setVisibility(1);
                sentFragment.setemails(database.mailDao().getMailBySender(sharedpreferences.getString("email", null)));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sentFragment).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Toast toast = Toast.makeText(getApplicationContext(),"logged out", Toast.LENGTH_SHORT);
        toast.show();
        exitapplication(view);
    }
    public void exitapplication(View view) {
        Intent intent = new Intent(FragmentSupportActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


            this.setContentView(R.layout.activity_main);
            NavigationView navigationView = (NavigationView)  this.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(FragmentSupportActivity.this);

    }

}