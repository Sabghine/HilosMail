package com.example.hilosmail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hilosmail.database.AppDataBase;
import com.example.hilosmail.entity.Mail;
import com.example.hilosmail.entity.User;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private AppDataBase database ;
    private List<Mail> mails;
    private Context myContext;
    private OnNoteListener mOnNotelistener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myitemview= LayoutInflater.from(myContext).inflate(R.layout.item_mail,parent,false);
        return new MyViewHolder(myitemview,mOnNotelistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        database = AppDataBase.getAppDatabase(myContext);
    Mail m = mails.get(position);
    User u = database.userDao().getUserByEmail(m.senderemail);
    holder.subject.setText(m.getSubject());
    holder.firstnameandlastname.setText(u.getFirstname()+" "+u.getLastname());
    holder.message.setText(m.getMessage());
    holder.date.setText(m.getSendingdate());
    holder.addressmail.setText(m.getSenderemail());
    holder.img.setBackgroundResource(R.drawable.ic_mail_profile_image_send_receive);
    }

    @Override
    public int getItemCount() {
        return mails.size();
    }
    public MyAdapter(Context context,List<Mail> mails,OnNoteListener onNoteListener)
    {
        this.mails = mails;
        this.myContext=context;
        this.mOnNotelistener = onNoteListener;
    }
    public Mail getItem (int position)
    {
        return mails.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        OnNoteListener onNoteListener;
        ImageView img;
        TextView firstnameandlastname;
        TextView subject;
        TextView message;
        TextView date;
        TextView addressmail;
        public MyViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            img = itemView.findViewById(R.id.item_mail_img);
            subject=itemView.findViewById(R.id.item_mail_title);
            firstnameandlastname=itemView.findViewById(R.id.first_and_lastname);
            message = itemView.findViewById(R.id.item_mail_content);
            date=itemView.findViewById(R.id.item_mail_date);
            addressmail=itemView.findViewById(R.id.item_mail_address);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener {
        void OnNoteClick(int position);
    }


}
