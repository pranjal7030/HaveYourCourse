package com.example.coursepool2;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommunityUploadAdapter extends RecyclerView.Adapter<CommunityUploadAdapter.ImageViewHolder> {
    List<CommunityUploadgettersetter>comdata;
    Context context;
    private String Url;

    public CommunityUploadAdapter(List<CommunityUploadgettersetter> comdata, Context context) {
        this.comdata = comdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.communityuploadview,parent,false);
        return new CommunityUploadAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final CommunityUploadgettersetter data = comdata.get(position);
        holder.name.setText(data.getName());
        holder.email.setText(data.getEmail());
        holder.date.setText(data.getDate());
        holder.capsta.setText(data.getFilecapstatus());
        String filetype=data.getType();
        if (filetype.equals("Image")){
            holder.comimage.setVisibility(View.VISIBLE);
            String  imageUrl = Constants.ROOT_URL+"CommunityFolder/"+"Images/"+data.getEmail()+"/"+ data.getFile();
            Picasso.with(context).load(imageUrl).into(holder.comimage);
        }
        if (filetype.equals("video")){
            holder.comvideo.setVisibility(View.VISIBLE);


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


                        holder.comvideo.setPlayer(simpleExoPlayer);
                        holder.comvideo.setKeepScreenOn(true);
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
            holder.comimage.setVisibility(View.GONE);
            holder.comvideo.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return comdata.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,date,capsta;
        ImageView comimage;
        PlayerView comvideo;
        public ImageViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.comname);
            email=itemView.findViewById(R.id.comemail);
            date=itemView.findViewById(R.id.comdate);
            capsta=itemView.findViewById(R.id.comcaptionstatus);
            comimage=itemView.findViewById(R.id.comimageview);
            comvideo=itemView.findViewById(R.id.comvideoview);
        }
    }
}
