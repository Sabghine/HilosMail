package com.example.hilosmail.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hilosmail.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertOne(User user);
    @Delete
    void delete(User user);

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE email =:email AND password=:password")
    User getUser(String email,String password);

    @Query("SELECT * FROM users WHERE email =:email")
    User getUserByEmail(String email);

    @Query("DELETE FROM users")
    public void nukeTable();
}
