package com.example.zimny.mp3;

import java.util.ArrayList;

/**
 * Created by ZimnY on 2016-02-03.
 */
public class DownloadList {


    public String link;
    public String nazwa;
    public Boolean play;


    public DownloadList(){

    }

    public DownloadList(String link, String nazwa) {


        this.nazwa = nazwa;
        this.link = link;
        this.play = false;
    }
    public String getLink()
    {
        return link;
    }
    public String getNazwa()
    {
        return nazwa;
    }
    public Boolean getPlay() {return play;}
    public void setPlay(Boolean play)
    {
        this.play=play;
    }
}