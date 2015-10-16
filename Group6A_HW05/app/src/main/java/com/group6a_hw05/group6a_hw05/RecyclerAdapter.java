package com.group6a_hw05.group6a_hw05;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arunkumar's on 10/15/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PodcastLinearViewHolder> {
    ArrayList<Podcast> fPodcastsForDisplay;
    Context fContext;

    public RecyclerAdapter(ArrayList<Podcast> fPodcastsForDisplay,Context aContext) {
        this.fPodcastsForDisplay = fPodcastsForDisplay;
        this.fContext = aContext;
    }

    public static class PodcastLinearViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rLPodcastItems;
        TextView tVTitle,tVDate;
        ImageView iVIcon;

        public PodcastLinearViewHolder(View itemView) {
            super(itemView);

            rLPodcastItems = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutPodcastItems);
            tVTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            tVDate = (TextView) itemView.findViewById(R.id.textViewDate);
            iVIcon = (ImageView) itemView.findViewById(R.id.imageViewIcon);
        }
    }

    @Override
    public PodcastLinearViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View lView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.podcast_row,viewGroup,false);
        PodcastLinearViewHolder lPodcastView = new PodcastLinearViewHolder(lView);
        return lPodcastView;
    }

    @Override
    public void onBindViewHolder(PodcastLinearViewHolder podcastViewHolder, int i) {
        String lImage = fPodcastsForDisplay.get(i).getImage();
        if (lImage!=null) {
            Picasso.with(fContext).load(lImage)
                    .resize(40, 40).into(podcastViewHolder.iVIcon);
        }
        else Picasso.with(fContext).load(R.drawable.no_image)
                .resize(40, 40).into(podcastViewHolder.iVIcon);
        podcastViewHolder.tVTitle.setText(fPodcastsForDisplay.get(i).getTitle());
        podcastViewHolder.tVDate.setText(fPodcastsForDisplay.get(i).getPublicationDate());
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
