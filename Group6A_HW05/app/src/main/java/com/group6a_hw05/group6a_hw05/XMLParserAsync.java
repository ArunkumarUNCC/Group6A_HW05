package com.group6a_hw05.group6a_hw05;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Arunkumar's on 10/14/2015.
 */
public class XMLParserAsync extends AsyncTask<String,Void,ArrayList<Podcast>> {
    IGetFeeds fActivity;
    ProgressDialog fProgress;
    String fPROGRESSMESSAGE = "Loading Episodes";

    public XMLParserAsync(IGetFeeds fActivity) {
        this.fActivity = fActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        fProgress = new ProgressDialog((Context) fActivity);
        fProgress.setMessage(fPROGRESSMESSAGE);
        fProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        fProgress.show();
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection lConnection = (HttpURLConnection) url.openConnection();
            lConnection.setRequestMethod("GET");

            lConnection.connect();

            int status = lConnection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream in = lConnection.getInputStream();

                return XMLParser.ParsePodcastFeeds.parsePodcasts(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Podcast> podcasts) {
        super.onPostExecute(podcasts);

        fProgress.dismiss();
        fActivity.putList(podcasts);
    }

    public static interface IGetFeeds{
        public void putList(ArrayList<Podcast> feeds);
    }
}
