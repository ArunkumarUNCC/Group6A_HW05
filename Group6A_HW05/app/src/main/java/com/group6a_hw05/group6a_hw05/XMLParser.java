package com.group6a_hw05.group6a_hw05;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Arunkumar's on 10/14/2015.
 */
public class XMLParser {

    public static class ParsePodcastFeeds extends DefaultHandler{
        Podcast podcast;
        ArrayList<Podcast> lPodcastsList;
        StringBuilder xmlInnerText;

        public ArrayList<Podcast> getPodcastsList() {
            return lPodcastsList;
        }

        static public ArrayList<Podcast> parsePodcasts(InputStream in) throws IOException, SAXException {
            ParsePodcastFeeds parser = new ParsePodcastFeeds();

            Xml.parse(in, Xml.Encoding.UTF_8, parser);

            return parser.getPodcastsList();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();

            lPodcastsList = new ArrayList<Podcast>();
            xmlInnerText = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if (localName.equals("item") || localName.equals("channel")){
                podcast = new Podcast();
            } else if(localName.equals("image")){
                podcast.setImage(attributes.getValue("href"));
            } else if(localName.equals("enclosure")){
                podcast.setAudio(attributes.getValue("url"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            if (localName.equals("item")){
                lPodcastsList.add(podcast);

            } else if(localName.equals("title")){
                podcast.setTitle(xmlInnerText.toString().trim());
            } else if(localName.equals("description")){
                podcast.setDescription(xmlInnerText.toString().trim());
            } else if(localName.equals("pubDate")){
                String[] lTempPubDate = xmlInnerText.toString().trim().split("[0-9]{2}:[0-9]{2}:[0-9]{2}");
                podcast.setPublicationDate(lTempPubDate[0]);
            } else if(localName.equals("duration")){
                podcast.setDuration(xmlInnerText.toString().trim());
            }

            xmlInnerText.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            xmlInnerText.append(ch,start,length);

            if (xmlInnerText.equals("itunes:image"))
                podcast.setImage("Hello");
        }

    }

}
