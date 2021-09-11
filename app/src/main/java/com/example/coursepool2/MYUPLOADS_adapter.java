package com.example.coursepool2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class MYUPLOADS_adapter extends RecyclerView.Adapter<MYUPLOADS_adapter.ImageViewHolder> {
    private Context context;
    private List<MYUPLOADS_getterSetter> myuploadsdata;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};
    private String Url;

    public MYUPLOADS_adapter(Context context, List<MYUPLOADS_getterSetter> myuploadsdata) {
        this.context = context;
        this.myuploadsdata = myuploadsdata;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myuploadsview,parent,false);
        return new MYUPLOADS_adapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final MYUPLOADS_getterSetter data = myuploadsdata.get(position);
        int randomcolour=getRandArrayElement();
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));
        holder.quesnsol.setText(data.getcap_and_status());
        holder.date.setText(data.getDate());


        String filetype=data.getType();
        if (filetype.equals("Image")){
            holder.comimaget.setVisibility(View.VISIBLE);
            String  imageUrl = Constants.ROOT_URL+"CommunityFolder/"+"Images/"+data.getEmail()+"/"+ data.getFile();
            Picasso.with(context).load(imageUrl).into(holder.comimaget);
        }
        if (filetype.equals("video")){
            holder.comvideot.setVisibility(View.VISIBLE);


            Url = Constants.ROOT_URL + "CommunityFolder/" +"Videos/"+data.getEmail()+"/"+data.getFile();
            Uri videoURL =Uri.parse(Url);

            LoadControl loadControl =new DefaultLoadControl();
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
            TrackSelector trackSelector =new DefaultTrackSelector(
                    new AdaptiveTrackSelection.Factory(bandwidthMeter)
            );
            SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    context, trackSelector, loadControl
            );
            DefaultHttpDataSourceFactory factory =new DefaultHttpDataSourceFactory(
                    "exoplayer_video"
            );
            ExtractorsFactory extractorsFactory =new DefaultExtractorsFactory();
            MediaSource mediaSource=new ExtractorMediaSource(videoURL
                    ,factory,extractorsFactory,null,null
            );


            holder.comvideot.setPlayer(simpleExoPlayer);
            holder.comvideot.setKeepScreenOn(true);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });
        }


        if (filetype.equals("post")){
            holder.comimaget.setVisibility(View.GONE);
            holder.comvideot.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return myuploadsdata.size();
    }
    public int getRandArrayElement()
    {
        return color[rand.nextInt(color.length)];
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView date, quesnsol;
        CardView cardView;
        ImageView comimaget;
        PlayerView comvideot;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardViewmur);
            date=itemView.findViewById(R.id.muDate);
            quesnsol=itemView.findViewById(R.id.mucapsta);
            comimaget=itemView.findViewById(R.id.comimageviewtracker);
            comvideot=itemView.findViewById(R.id.comvideoviewtracker);
        }
    }
}
