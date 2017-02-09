package com.example.zimny.mp3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZimnY on 2016-02-03.
 */
public class DownloadListAdapter extends ArrayAdapter<DownloadList> {

    Context context;
    int layoutResourceId;
    List<DownloadList> data = null;
    MediaPlayer mediaPlayer = new MediaPlayer();
    DowloadListHolder holder = null;
    ImageButton lastimagebutton;

    public DownloadListAdapter(Context context, int layoutResourceId,List<DownloadList> data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DowloadListHolder();
            holder.dowloadButton = (ImageButton)row.findViewById(R.id.DownloadButton);
            holder.playButton = (ImageButton)row.findViewById(R.id.PlayButton);
            holder.linkView = (TextView)row.findViewById(R.id.linkView);
            holder.playButton.setImageResource(R.drawable.play);
            String object = data.get(position).getNazwa();
            holder.linkView.setText(object);
            holder.dowloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(data.get(position).getLink()));
                        context.startActivity(i);
                    }
                    catch(IllegalStateException e)
                    {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            lastimagebutton.setImageResource(R.drawable.play);
                        }
                        ImageButton imageButton = (ImageButton) v;
                        if (lastimagebutton != imageButton) {
                            mediaPlayer.reset();
                            imageButton.setImageResource(R.drawable.pause);
                            lastimagebutton = imageButton;
                            Map<String, String> headerMap = new HashMap<String, String>();
                            headerMap.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900F Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
                            Uri uri = Uri.parse(data.get(position).getLink());
                            mediaPlayer.setDataSource(context, uri, headerMap);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } else {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            imageButton.setImageResource(R.drawable.play);
                            lastimagebutton = null;
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }




                }

            });
            row.setTag(holder);
        }
        else
        {
            holder = (DowloadListHolder)row.getTag();
        }




       


        return row;
    }

    public void add(String link, String nazwa)
    {
        data.add(new DownloadList(link,nazwa));
    }


    static class DowloadListHolder
    {
        ImageButton dowloadButton;
        ImageButton playButton;
        TextView linkView;
    }
}