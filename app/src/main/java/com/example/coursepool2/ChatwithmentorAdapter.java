package com.example.coursepool2;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.view.Gravity.END;
import static android.view.Gravity.RIGHT;
import static android.view.Gravity.TOP;

public class ChatwithmentorAdapter extends RecyclerView.Adapter<ChatwithmentorAdapter.ImageViewHolder> {

   List<ChatwithmentorGetterSetter> chatdata;
   Context context;



    public ChatwithmentorAdapter(Context applicationContext, List<ChatwithmentorGetterSetter> chatdat) {
        this.chatdata = chatdat;
        this.context=applicationContext;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatview,parent,false);
        return new ChatwithmentorAdapter.ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final ChatwithmentorGetterSetter data = chatdata.get(position);
        try {


            if (data.getType().equals("user")) {

                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.mchat.setText(data.getMessage());
                holder.mdate.setText(data.getDate());
            } else if (data.getType().equals("teacher")) {

                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.blue));
                holder.mchat.setText(data.getMessage());
                holder.mdate.setText(data.getDate());
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return chatdata.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView mchat,mdate;
        CardView cardView;
        LinearLayout view;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            mchat=itemView.findViewById(R.id.chatTeacher);
            mdate=itemView.findViewById(R.id.Date);
            cardView =itemView.findViewById(R.id.cardView);
            view=itemView.findViewById(R.id.view);


        }
    }
}
