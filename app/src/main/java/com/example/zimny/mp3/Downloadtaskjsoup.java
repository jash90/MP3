package com.example.zimny.mp3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZimnY on 2016-01-28.
 */
public class Downloadtaskjsoup extends AsyncTask<String,String,ArrayList> {


    @Override
    protected ArrayList doInBackground(String... params) {


        ArrayList linki = new ArrayList();

        String szuk = params[0];
        String numer = params[1];
        String url = "http://mp3.teledyski.info/index.html?text=" + szuk + "&strona="+numer +"&file=pmobile&name=eplik&dalej=Szukaj";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Linux; Android 5.0; SM-G900F Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36").get();

        Elements links = doc.select("a[href]");


        for (Element link : links) {
            if (link.attr("abs:href").contains("open-playlist"))
                linki.add(String.valueOf(link.attr("abs:href").substring(0, link.attr("abs:href").indexOf("?")) + " " + link.text()));
            if (link.text().contains(">>"))
                linki.add(0,link.attr("abs:href"));
        }




        } catch (IOException e) {
            e.printStackTrace();
        }
        return linki;

    }
}


