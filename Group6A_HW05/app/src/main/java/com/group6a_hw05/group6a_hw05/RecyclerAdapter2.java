package com.group6a_hw05.group6a_hw05;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;


public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.PodcastGridViewHolder> {
    final String fGOTOPLAYACTIVITY = "com.group6a_hw05.group6a_hw05.intent.action.PLAYACTIVITY";
    final String fPODCASTREF = "PodcastRef";

    ArrayList<Podcast> fPodcastsForDisplay;
    Context fContext;
    public static MediaPlayer fMediaPlayer;

    public RecyclerAdapter2(ArrayList<Podcast> aPodcastsForDisplay, Context aContext) {
        this.fPodcastsForDisplay = aPodcastsForDisplay;
        this.fContext = aContext;
    }

    public static class PodcastGridViewHolder extends RecyclerView.ViewHolder{
        LinearLayout lLPodcastItems;
        TextView tVTitle;
        ImageView iVIcon,iVPlay;

        public PodcastGridViewHolder(View itemView) {
            super(itemView);

            lLPodcastItems = (LinearLayout) itemView.findViewById(R.id.linearLayoutPodcastGrid);
            iVIcon = (ImageView) itemView.findViewById(R.id.imageViewPodcastIcon);
            tVTitle = (TextView) itemView.findViewById(R.id.textViewPodcastTitle);
            iVPlay = (ImageView) itemView.findViewById(R.id.imageViewPlayButtonGrid);
        }
    }

    @Override
    public PodcastGridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View lView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.podcast_grid,viewGroup,false);
        PodcastGridViewHolder lPodcastView = new PodcastGridViewHolder(lView);
        return lPodcastView;
    }

    @Override
    public void onBindViewHolder(PodcastGridViewHolder podcastViewHolder, final int i) {
        String lImage = fPodcastsForDisplay.get(i).getImage();
        if (lImage!=null) {
            Picasso.with(fContext).load(lImage)
                    .resize(40, 40).into(podcastViewHolder.iVIcon);
        }
        else Picasso.with(fContext).load(R.drawable.no_image)
                .resize(40, 40).into(podcastViewHolder.iVIcon);
        podcastViewHolder.tVTitle.setText(fPodcastsForDisplay.get(i).getTitle());

        podcastViewHolder.tVTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayActivity(fPodcastsForDisplay.get(i));
            }
        });

        podcastViewHolder.iVPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(fPodcastsForDisplay.get(i).getAudio());
            }
        });

    }

    @Override
    public int getItemCount() {
        return fPodcastsForDisplay.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    //Function to start with Play Activity
    public void startPlayActivity(Podcast apodcastItem){
        Intent intent = new Intent(fGOTOPLAYACTIVITY);
        intent.putExtra(fPODCASTREF,apodcastItem);
        fContext.startActivity(intent);
    }


    //Function to implement play audio
    public void playAudio(String aAudioStreamLink){
        fMediaPlayer = new MediaPlayer();
        try {
            fMediaPlayer.setDataSource(aAudioStreamLink);
            fMediaPlayer.prepare();
            fMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.playing();
    }

    public static MediaPlayer getMediaPlayer(){
        return fMediaPlayer;
    }
}
