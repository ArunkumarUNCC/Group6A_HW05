package com.group6a_hw05.group6a_hw05;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arunkumar's on 10/15/2015.
 */
public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.PodcastGridViewHolder> {
    ArrayList<Podcast> fPodcastsForDisplay;
    Context fContext;

    public RecyclerAdapter2(ArrayList<Podcast> fPodcastsForDisplay,Context aContext) {
        this.fPodcastsForDisplay = fPodcastsForDisplay;
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
        }
    }

    @Override
    public PodcastGridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View lView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.podcast_grid,viewGroup,false);
        PodcastGridViewHolder lPodcastView = new PodcastGridViewHolder(lView);
        return lPodcastView;
    }

    @Override
    public void onBindViewHolder(PodcastGridViewHolder podcastViewHolder, int i) {
        String lImage = fPodcastsForDisplay.get(i).getImage();
        if (lImage!=null) {
            Picasso.with(fContext).load(lImage)
                    .resize(40, 40).into(podcastViewHolder.iVIcon);
        }
        else Picasso.with(fContext).load(R.drawable.no_image)
                .resize(40, 40).into(podcastViewHolder.iVIcon);
        podcastViewHolder.tVTitle.setText(fPodcastsForDisplay.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        return fPodcastsForDisplay.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
