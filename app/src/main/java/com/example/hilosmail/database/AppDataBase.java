package com.example.hilosmail.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hilosmail.dao.CategoryDao;
import com.example.hilosmail.dao.MailDao;
import com.example.hilosmail.dao.UserDao;
import com.example.hilosmail.entity.Category;
import com.example.hilosmail.entity.Mail;
import com.example.hilosmail.entity.User;

@Database(entities = {User.class, Mail.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;
    public abstract UserDao userDao();
    public abstract MailDao mailDao();
    public abstract CategoryDao categoryDao();
    public static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "room_test_db")

                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
