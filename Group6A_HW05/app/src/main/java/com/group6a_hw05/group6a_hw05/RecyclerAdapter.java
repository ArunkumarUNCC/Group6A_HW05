package com.group6a_hw05.group6a_hw05;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PodcastLinearViewHolder> {
    ArrayList<Podcast> fPodcastsForDisplay;
    Context fContext;

    public RecyclerAdapter(ArrayList<Podcast> fPodcastsForDisplay,Context aContext) {
        this.fPodcastsForDisplay = fPodcastsForDisplay;
        this.fContext = aContext;
    }

    public static class PodcastLinearViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout lPodcastItems;
        TextView lTitle, lDate;
        ImageView lIcon;

        public PodcastLinearViewHolder(View aItemView) {
            super(aItemView);

            lPodcastItems = (RelativeLayout) aItemView.findViewById(R.id.relativeLayoutPodcastItems);
            lTitle = (TextView) aItemView.findViewById(R.id.textViewTitle);
            lDate = (TextView) aItemView.findViewById(R.id.textViewDate);
            lIcon = (ImageView) aItemView.findViewById(R.id.imageViewIcon);
        }
    }

    @Override
    public PodcastLinearViewHolder onCreateViewHolder(ViewGroup aViewGroup, int i) {
        View lView = LayoutInflater.from(aViewGroup.getContext()).inflate(R.layout.podcast_row, aViewGroup, false);
        PodcastLinearViewHolder lPodcastView = new PodcastLinearViewHolder(lView);
        return lPodcastView;
    }

    @Override
    public void onBindViewHolder(PodcastLinearViewHolder aPodcastViewHolder, int i) {
        String lImage = fPodcastsForDisplay.get(i).getImage();
        if (lImage!=null) {
            Picasso.with(fContext).load(lImage)
                    .resize(40, 40).into(aPodcastViewHolder.lIcon);
        }
        else Picasso.with(fContext).load(R.drawable.no_image)
                .resize(40, 40).into(aPodcastViewHolder.lIcon);
        aPodcastViewHolder.lTitle.setText(fPodcastsForDisplay.get(i).getTitle());
        aPodcastViewHolder.lDate.setText(fPodcastsForDisplay.get(i).getPublicationDate());
    }

    @Override
    public int getItemCount() {
        return fPodcastsForDisplay.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView aRecyclerView) {
        super.onAttachedToRecyclerView(aRecyclerView);
    }
}
