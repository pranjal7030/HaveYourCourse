package com.example.coursepool2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Pay_n_enroll_review_adapter extends RecyclerView.Adapter<Pay_n_enroll_review_adapter.ImageViewholder> {
        List<Pay_n_enroll_review_gettersetter> reviewslist;
        Context context;

    public Pay_n_enroll_review_adapter(List<Pay_n_enroll_review_gettersetter> reviewslist, Context context) {
        this.reviewslist = reviewslist;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewforreview,parent,false);
        return new Pay_n_enroll_review_adapter.ImageViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewholder holder, int position) {
        final Pay_n_enroll_review_gettersetter data = reviewslist.get(position);
        holder.name.setText(data.getName());
        holder.date.setText(data.getDate());
        holder.review.setText(data.getReview());
        holder.rate.setRating(Float.parseFloat(data.getRate()));


    }

    @Override
    public int getItemCount() {
        return reviewslist.size();
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {
        TextView name,date,review;
        RatingBar rate;
        public ImageViewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.pereviewname);
            date=itemView.findViewById(R.id.pereviewdate);
            review=itemView.findViewById(R.id.peuserreview);
            rate=itemView.findViewById(R.id.peuserreviewrate);
        }
    }
}
