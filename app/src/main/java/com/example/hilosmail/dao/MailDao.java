package com.example.hilosmail.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hilosmail.entity.Mail;
import com.example.hilosmail.entity.User;

import java.util.List;

@Dao
public interface MailDao {
    @Insert
    void insertOne(Mail mail);
    @Delete
    void delete(Mail mail);

    @Query("SELECT * FROM emails ORDER BY sending_date desc")
    List<Mail> getAll();

    @Query("SELECT * FROM emails WHERE sender_email =:sender AND receiver_email=:receiver ORDER BY sending_date desc ")
    Mail getMail(String sender,String receiver);

    @Query("SELECT * FROM emails WHERE receiver_email =:receiver ORDER BY sending_date desc")
    List<Mail> getMailByReceiver(String receiver);

    @Query("SELECT * FROM emails WHERE sender_email =:sender ORDER BY sending_date desc")
    List<Mail> getMailBySender(String sender);

    @Query("UPDATE emails SET category=:category WHERE sender_email=:senderemail")
    void UpdateMailsCategory (String category,String senderemail);

    @Query("SELECT * FROM emails WHERE receiver_email =:receiver AND category=:category ORDER BY sending_date desc")
    List<Mail> getMailByReceiverAndCategory(String receiver,String category);

    @Query("DELETE FROM emails")
    public void nukeTable();

    @Query("UPDATE emails SET category=:category WHERE receiver_email=:receiver AND sender_email=:sender")
    public void deletecategoryfrommails(String category,String receiver,String sender);

    @Query("SELECT category FROM emails WHERE sender_email =:sender AND receiver_email= :receiver LIMIT 1")
    public String getcategoryofsenderforreceiver(String sender,String receiver);

    @Query("SELECT sender_email FROM emails WHERE category=:category")
    public String getsenderbycategory(String category);
}
