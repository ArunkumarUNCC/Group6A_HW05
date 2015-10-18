package com.group6a_hw05.group6a_hw05;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PodcastLinearViewHolder> {
    final String fGOTOPLAYACTIVITY = "com.group6a_hw05.group6a_hw05.intent.action.PLAYACTIVITY";
    final String fPODCASTREF = "PodcastRef";

    ArrayList<Podcast> fPodcastsForDisplay;
    Context fContext;
    public static MediaPlayer fMediaPlayer;

    public RecyclerAdapter(ArrayList<Podcast> fPodcastsForDisplay,Context aContext) {
        this.fPodcastsForDisplay = fPodcastsForDisplay;
        this.fContext = aContext;
    }

    public static class PodcastLinearViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rLPodcastItems;
        TextView tVTitle, tVDate;
        ImageView iVIcon,iVPlay;

        public PodcastLinearViewHolder(View aItemView) {
            super(aItemView);

            rLPodcastItems = (RelativeLayout) aItemView.findViewById(R.id.relativeLayoutPodcastItems);
            tVTitle = (TextView) aItemView.findViewById(R.id.textViewTitle);
            tVDate = (TextView) aItemView.findViewById(R.id.textViewDate);
            iVIcon = (ImageView) aItemView.findViewById(R.id.imageViewIcon);
            iVPlay = (ImageView) aItemView.findViewById(R.id.imageViewPlayButton);

        }
    }

    @Override
    public PodcastLinearViewHolder onCreateViewHolder(ViewGroup aViewGroup, int i) {
        View lView = LayoutInflater.from(aViewGroup.getContext()).inflate(R.layout.podcast_row, aViewGroup, false);
        PodcastLinearViewHolder lPodcastView = new PodcastLinearViewHolder(lView);
        return lPodcastView;
    }

    @Override
    public void onBindViewHolder(PodcastLinearViewHolder aPodcastViewHolder, final int i) {
        String lImage = fPodcastsForDisplay.get(i).getImage();
        if (lImage != null) {
            Picasso.with(fContext).load(lImage)
                    .resize(40, 40).into(aPodcastViewHolder.iVIcon);
        }
        else Picasso.with(fContext).load(R.drawable.no_image)
                .resize(40, 40).into(aPodcastViewHolder.iVIcon);
        aPodcastViewHolder.tVTitle.setText(fPodcastsForDisplay.get(i).getTitle());
        aPodcastViewHolder.tVDate.setText(fPodcastsForDisplay.get(i).getPublicationDate());

        aPodcastViewHolder.tVTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayActivity(fPodcastsForDisplay.get(i));
            }
        });

        aPodcastViewHolder.iVPlay.setOnClickListener(new View.OnClickListener() {
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
    public void onAttachedToRecyclerView(RecyclerView aRecyclerView) {
        super.onAttachedToRecyclerView(aRecyclerView);
    }

    //Function to start with Play Activity
    public void startPlayActivity(Podcast aPodcastItem){
        Intent intent = new Intent(fGOTOPLAYACTIVITY);
        intent.putExtra(fPODCASTREF,aPodcastItem);
        fContext.startActivity(intent);
    }


    //Function to implement play audio
    public void playAudio(String aAudioStreamLink ){
        if(true){
            fMediaPlayer = new MediaPlayer();
            try {
                fMediaPlayer.setDataSource(aAudioStreamLink);
                fMediaPlayer.prepare();
                fMediaPlayer.start();
//                fIsPlayed = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            fMediaPlayer.start();
        }
//        fMediaPlayer.getc
        MainActivity.playing();
    }

    public static MediaPlayer getMediaPlayer(){
        return fMediaPlayer;
    }
}
