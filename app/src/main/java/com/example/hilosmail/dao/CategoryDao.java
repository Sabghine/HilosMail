package com.example.hilosmail.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hilosmail.entity.Category;

import java.util.List;
@Dao
public interface CategoryDao {
    @Insert
    void insertOne(Category categorie);
    @Delete
    void delete(Category categorie);

    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE category_name =:categoriename")
    Category getMail(String categoriename);

    @Query("SELECT * FROM categories WHERE user_id =:userid")
    List<Category> getCategoryByUser(int userid);

    @Query("DELETE FROM categories WHERE category_name =:categoriename AND user_id=:connected")
    void DeleteCategory(String categoriename,int connected);

    @Query("DELETE FROM categories")
    public void nukeTable();
}
