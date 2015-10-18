package com.group6a_hw05.group6a_hw05;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    TextView fEpisodeTitle, fDescription, fDate, fDuration;
    ImageView fEpisodeIcon, fPlayButton, fPauseButton;
    Podcast fPodcastData;
    String fAudioFile;
    MediaPlayer fMediaPlayer;
    Boolean fIsPlayed;
    ProgressBar fEpisodeProgress;
    final String fPODCASTREF = "PodcastRef";
    static Handler fHandler;
    int fEpisodeDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        fMediaPlayer = new MediaPlayer();

        //displaying app icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ted_icon);

        findItems();
        populateData(fPodcastData);

        fPauseButton.setVisibility(View.INVISIBLE);
        fIsPlayed = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playOnClick(View aView){
        if(!fIsPlayed){
            fMediaPlayer.setOnPreparedListener(this);
            try {
                fMediaPlayer.setDataSource(fAudioFile);
                fMediaPlayer.prepare();
                fMediaPlayer.start();
                fIsPlayed = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            fMediaPlayer.start();
        }

        fHandler = new Handler();
        PlayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (fMediaPlayer!=null){
                    int lCurrentPosition = ((fMediaPlayer.getCurrentPosition())*100)/fEpisodeDuration;
                    fEpisodeProgress.setProgress(lCurrentPosition);
                }

                fHandler.postDelayed(this,1000);
            }
        });

        fPlayButton.setVisibility(View.INVISIBLE);
        fPauseButton.setVisibility(View.VISIBLE);
    }

    public void pauseOnClick(View aView){
        fMediaPlayer.pause();

        fPauseButton.setVisibility(View.INVISIBLE);
        fPlayButton.setVisibility(View.VISIBLE);

    }

    public void populateData(Podcast aPodcast){
        fEpisodeTitle.setText(aPodcast.getTitle());
        fDescription.setText("Description: " + aPodcast.getDescription());
        fDate.setText("Publication Date: " + aPodcast.getPublicationDate());
        fDuration.setText("Duration: " + Math.round(Double.parseDouble(aPodcast.getDuration())/60*100.0)/100.0+" minutes");
        fAudioFile = aPodcast.getAudio();
        Picasso.with(this).load(aPodcast.getImage()).into(fEpisodeIcon);

        fEpisodeDuration = Integer.parseInt(aPodcast.getDuration()) * 1000;
        fEpisodeProgress.setMax(100);
    }

    public void findItems(){
        fEpisodeTitle = (TextView) findViewById(R.id.textViewEpisodeTitle);
        fDescription = (TextView) findViewById(R.id.textViewEpisodeDescription);
        fDate = (TextView) findViewById(R.id.textViewDescripDate);
        fDuration = (TextView) findViewById(R.id.textViewDuration);
        fEpisodeIcon = (ImageView) findViewById(R.id.imageViewEpisodeIcon);
        fPodcastData = (Podcast) getIntent().getSerializableExtra(fPODCASTREF);
        fPlayButton = (ImageView) findViewById(R.id.imageViewPlayButton);
        fPauseButton = (ImageView) findViewById(R.id.imageViewPauseButton);
        fEpisodeProgress = (ProgressBar) findViewById(R.id.progressBarEpisodeLength);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fMediaPlayer.stop();
        this.finish();
    }
}
