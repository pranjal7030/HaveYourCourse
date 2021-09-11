package com.example.coursepool2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class Activitytracker_Adapter extends RecyclerView.Adapter<Activitytracker_Adapter.ImageViewholder> {
    private Context context;
    private List<Activitytracker_GetterSetter> activitytrackerdata;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};
    private String checkquesol;


    public Activitytracker_Adapter(Context context, List<Activitytracker_GetterSetter> activitytrackerdata) {
        this.context = context;
        this.activitytrackerdata = activitytrackerdata;
    }

    @NonNull
    @Override
    public ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tracker_view,parent,false);
        return new Activitytracker_Adapter.ImageViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewholder holder, int position) {
        final Activitytracker_GetterSetter data = activitytrackerdata.get(position);
        int randomcolour=getRandArrayElement();
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));
        holder.date.setText(data.getDate());

        holder.coursetitle.setText(data.getCoursetilte());
        holder.videotitle.setText(data.getVideotitle());
        checkquesol=data.getQues_and_ans();
        if (checkquesol.equals("")){
            holder.quesofsol.setText(data.getQuestion_of_solution());
            holder.textsols.setVisibility(View.GONE);
            holder.solution2.setVisibility(View.GONE);

        }
       else if (!checkquesol.equals("")){
            holder.quesnsol.setText(data.getQues_and_ans());
            holder.quesofsol.setText(data.getQuestion_of_solution());

        }
       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


           }
       });


    }

    @Override
    public int getItemCount() {
        return activitytrackerdata.size();
    }
    public int getRandArrayElement()
    {
        return color[rand.nextInt(color.length)];
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {
        TextView date,quesnsol,coursetitle,videotitle,quesofsol,textsols,edit;
        CardView cardView;
        TextView solution2;

        public ImageViewholder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardViewtracker);
            date=itemView.findViewById(R.id.actDate);
            quesnsol=itemView.findViewById(R.id.actqna);
            coursetitle=itemView.findViewById(R.id.actqnacoursetitle);
            videotitle=itemView.findViewById(R.id.actqnavideotitle);
            quesofsol=itemView.findViewById(R.id.actqnaquesofsol);
            textsols=itemView.findViewById(R.id.textsol);
            solution2=itemView.findViewById(R.id.actqna);
            edit=itemView.findViewById(R.id.textsoledit);


        }
    }
}
