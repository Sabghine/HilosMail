package com.example.hilosmail.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "emails")
public class Mail {
    @PrimaryKey(autoGenerate = true)
    public int id ;
    @ColumnInfo(name = "subject")
    public String subject;

    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "sender_email")
    public String senderemail;

    @ColumnInfo(name = "receiver_email")
    public String receiveremail;

    @ColumnInfo(name = "sending_date")
    public String sendingdate;



    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name ="mail_image")
    public int image;
    public Mail()
    {

    }

    public Mail(String subject, String message, String senderemail, String receiveremail,String sendingdate) {
        this.subject = subject;
        this.message = message;
        this.senderemail = senderemail;
        this.receiveremail = receiveremail;
        this.sendingdate = sendingdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderemail() {
        return senderemail;
    }

    public void setSenderemail(String senderemail) {
        this.senderemail = senderemail;
    }

    public String getReceiveremail() {
        return receiveremail;
    }

    public void setReceiveremail(String receiveremail) {
        this.receiveremail = receiveremail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSendingdate() {
        return sendingdate;
    }

    public void setSendingdate(String sendingdate) {
        this.sendingdate = sendingdate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
