package com.group6a_hw05.group6a_hw05;

import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    TextView fEpisodeTitle, fDescription, fDate, fDuration;
    ImageView fEpisodeIcon, fPlayButton, fPauseButton;
    Podcast fPodcastData;
    String fAudioFile;
    MediaPlayer fMediaPlayer = new MediaPlayer();
    final String fPODCASTREF = "PodcastRef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //displaying app icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ted_icon);

        findItems();
        populateData(fPodcastData);

        fPauseButton.setVisibility(View.INVISIBLE);

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
        fMediaPlayer.setOnPreparedListener(this);
        try {
            fMediaPlayer.setDataSource(fAudioFile);
            fMediaPlayer.prepare();
            fMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fPlayButton.setVisibility(View.INVISIBLE);
        fPauseButton.setVisibility(View.VISIBLE);
    }

    public void pauseOnClick(View aView){
        fMediaPlayer.stop();
        fMediaPlayer.release();
        fMediaPlayer.release();

        fPauseButton.setVisibility(View.INVISIBLE);
        fPlayButton.setVisibility(View.VISIBLE);

    }

    public void populateData(Podcast aPodcast){
        fEpisodeTitle.setText(aPodcast.getTitle());
        fDescription.setText("Description: " + aPodcast.getDescription());
        fDate.setText("Publication Date: " + aPodcast.getPublicationDate());
        fDuration.setText("Duration: " + aPodcast.getDuration());
        fAudioFile = aPodcast.getAudio();
        Picasso.with(this).load(aPodcast.getImage()).into(fEpisodeIcon);
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
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
