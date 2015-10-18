package com.group6a_hw05.group6a_hw05;
//Michael Vitulli
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements XMLParserAsync.IGetFeeds{

    ArrayList<Podcast> fPodcastList;
    String fCurrentView = "Linear";
    public static ImageView fPlayButton, fPauseButton;
    public static ProgressBar fEpisodeProgress;
    RecyclerView fRecycler;
    LinearLayoutManager fRecyclerLayout;
    static Boolean fIsPlaying = false;
    static Handler fHandler;

    private final String fPODCAST_RSS = "http://www.npr.org/rss/podcast.php?id=510298";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        fRecycler = (RecyclerView) findViewById(R.id.RecyclerView);
        fPlayButton = (ImageView) findViewById(R.id.imageViewPlayButton);
        fPauseButton = (ImageView) findViewById(R.id.imageViewPauseButton);
        fEpisodeProgress = (ProgressBar) findViewById(R.id.progressBarEpisodeLength);

        fPlayButton.setVisibility(View.INVISIBLE);
        fPauseButton.setVisibility(View.INVISIBLE);
        fEpisodeProgress.setVisibility(View.INVISIBLE);

        new XMLParserAsync(this).execute(fPODCAST_RSS);

        //displaying app icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ted_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.RecycleViews:
                if(fCurrentView.equals("Linear")){
                    fCurrentView = "Grid";
                }
                else{
                    fCurrentView = "Linear";
                }

                fPlayButton.setVisibility(View.INVISIBLE);
                fPauseButton.setVisibility(View.INVISIBLE);
                fEpisodeProgress.setVisibility(View.INVISIBLE);


                setRecyclerView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void putList(ArrayList<Podcast> feeds) {
        fPodcastList = new ArrayList<Podcast>();
        fPodcastList = feeds;

        setRecyclerView();
    }

    public void setRecyclerView(){
        if (fCurrentView.equals("Linear")){
            fRecyclerLayout = new LinearLayoutManager(this);
            fRecycler.setLayoutManager(fRecyclerLayout);

            RecyclerAdapter lRecyclerAdapter = new RecyclerAdapter(fPodcastList, MainActivity.this);
            fRecycler.setAdapter(lRecyclerAdapter);

                if (fIsPlaying) {
                    RecyclerAdapter2.getMediaPlayer().stop();

                    fIsPlaying = false;
                }

        }
        else{
            fRecyclerLayout = new GridLayoutManager(this,2);
            fRecycler.setLayoutManager(fRecyclerLayout);

            RecyclerAdapter2 lRecyclerAdapter = new RecyclerAdapter2(fPodcastList, MainActivity.this);
            fRecycler.setAdapter(lRecyclerAdapter);

                if (fIsPlaying) {
                    RecyclerAdapter.getMediaPlayer().stop();

                    fIsPlaying = false;
                }

        }
    }

    public void playing(){
        fPauseButton.setVisibility(View.VISIBLE);
        fPlayButton.setVisibility(View.INVISIBLE);
        fEpisodeProgress.setVisibility(View.VISIBLE);
        fIsPlaying = true;

        fHandler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int lCurrentPosition = 0;
                if(fCurrentView.equals("Linear")){
                    if (RecyclerAdapter.getMediaPlayer() != null){
                        lCurrentPosition = ((RecyclerAdapter.getMediaPlayer().getCurrentPosition()) * 100) / RecyclerAdapter.getMediaPlayer().getDuration();
                    }
                }else{
                    if (RecyclerAdapter2.getMediaPlayer() != null){
                        lCurrentPosition = ((RecyclerAdapter2.getMediaPlayer().getCurrentPosition()) * 100) / RecyclerAdapter2.getMediaPlayer().getDuration();
                    }
                }

                fEpisodeProgress.setProgress(lCurrentPosition);
                fHandler.postDelayed(this, 1000);
            }
        });



    }
    public void playOnClick(View aView){
        if(fCurrentView.equals("Linear"))
            RecyclerAdapter.getMediaPlayer().start();
        else
            RecyclerAdapter2.getMediaPlayer().start();

        fPauseButton.setVisibility(View.VISIBLE);
        fPlayButton.setVisibility(View.INVISIBLE);
    }

    public void pauseOnClick(View aView){
        if(fCurrentView.equals("Linear"))
            RecyclerAdapter.getMediaPlayer().pause();
        else
            RecyclerAdapter2.getMediaPlayer().pause();

        fPauseButton.setVisibility(View.INVISIBLE);
        fPlayButton.setVisibility(View.VISIBLE);
    }
}
