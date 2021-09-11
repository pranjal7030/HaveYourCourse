package com.example.coursepool2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class UserHelpcenterAdapter extends RecyclerView.Adapter<UserHelpcenterAdapter.ImageViewHolder> {
    private Context context;
    private List<UserHelpcentergettersetter> helpdata;
    Random rand = new Random();
    int[] color={R.color.darkblue,R.color.orange,R.color.green,R.color.red,R.color.yellow};

    public UserHelpcenterAdapter(Context context,List<UserHelpcentergettersetter> helpdata){
        this.context=context;
        this.helpdata=helpdata;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userhelpcenterview_view,parent,false);
        return new UserHelpcenterAdapter.ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final UserHelpcentergettersetter data = helpdata.get(position);
        holder.app.setText(data.getAppreciation());
        holder.feed.setRating(Float.parseFloat(data.getFeedback()));
        holder.date.setText(data.getDate());
        holder.name.setText(data.getName());
        int randomcolour=getRandArrayElement();
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));

    }

    @Override
    public int getItemCount() {
        return helpdata.size();
    }
    public int getRandArrayElement() {
        return color[rand.nextInt(color.length)];
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView app,date,name;
        RatingBar feed;
        CardView cardView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            app=itemView.findViewById(R.id.Appreciation);
            feed=itemView.findViewById(R.id.Feedback);
            date=itemView.findViewById(R.id.Date);
            name=itemView.findViewById(R.id.namehelp);
            cardView=itemView.findViewById(R.id.cardViewhelpcenter);

        }
    }
}
