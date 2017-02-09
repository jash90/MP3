package com.example.zimny.mp3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button,prev,next;
    ListView listView;
    DownloadListAdapter adapter;
    TextView textView;
    List<DownloadList> pliki;
    String szuk;
    int bierzacy=0;
    int iloscstron=0;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listView);
        textView = (TextView)findViewById(R.id.textView);
        prev = (Button)findViewById(R.id.prev);
        next = (Button)findViewById(R.id.next);
        pliki = new ArrayList<DownloadList>();
        szuk="";
        if (bierzacy==0)
            prev.setVisibility(View.INVISIBLE);
        if (iloscstron==0)
            next.setVisibility(View.INVISIBLE);
        adapter = new DownloadListAdapter(this,R.layout.row, pliki);

        listView.setAdapter(adapter);
        context=this;
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    bierzacy = 0;
                    ArrayList lista = null;
                    try {
                        lista = new Downloadtaskjsoup().execute( Html.escapeHtml(editText.getText().toString()), String.valueOf(bierzacy)).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    next.setVisibility(View.VISIBLE);
                    adapter.clear();

                    iloscstron = Integer.valueOf(lista.get(0).toString().substring(lista.get(0).toString().lastIndexOf("=") + 1, lista.get(0).toString().length()));
                    textView.setText(String.valueOf(bierzacy + 1) + " / " + String.valueOf(iloscstron));
                    szuk =  Html.escapeHtml(editText.getText().toString());

                    lista.remove(0);
                    for (Object words : lista) {
                        String s = words.toString().substring(words.toString().indexOf(" ") + 1);
                        String s2 = words.toString().substring(0, words.toString().indexOf(" ") + 1);
                        getlinkandname(s2,s);

                    }

                }
                return false;
            }
        });
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }




    public void ButtonClick(View view) throws ExecutionException, InterruptedException
    {

        bierzacy=0;
        ArrayList lista=  new Downloadtaskjsoup().execute(Html.escapeHtml(editText.getText().toString()),String.valueOf(bierzacy)).get();
        next.setVisibility(View.VISIBLE);
        adapter.clear();

        iloscstron= Integer.valueOf(lista.get(0).toString().substring(lista.get(0).toString().lastIndexOf("=") + 1, lista.get(0).toString().length()));
        textView.setText(String.valueOf(bierzacy+1)+" / "+String.valueOf(iloscstron));
        szuk= Html.escapeHtml(editText.getText().toString());
        for (Object words: lista)
        {
            String s = words.toString().substring(words.toString().indexOf(" ") + 1);
            String s2 = words.toString().substring(0,words.toString().indexOf(" ")+1);
            getlinkandname(s2,s);
        }
        try {
            Log.d("moj", URLEncoder.encode(editText.getText().toString(),"iso-8859-2"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public void getlinkandname(String link,String name)
    {
        adapter.add(link,name);
    }


    public void NextButton(View view) throws ExecutionException, InterruptedException
    {
        bierzacy=bierzacy+1;
        if (bierzacy==0)
            prev.setVisibility(View.INVISIBLE);
        else
            prev.setVisibility(View.VISIBLE);
        if (bierzacy+1==iloscstron)
            next.setVisibility(View.INVISIBLE);
        else
            next.setVisibility(View.VISIBLE);
        ArrayList lista=  new Downloadtaskjsoup().execute(szuk,String.valueOf(bierzacy)).get();
        adapter.clear();

        iloscstron= Integer.valueOf(lista.get(0).toString().substring(lista.get(0).toString().lastIndexOf("=") + 1, lista.get(0).toString().length()));
        textView.setText(String.valueOf(bierzacy+1)+" / "+String.valueOf(iloscstron));
        lista.remove(0);
        for (Object words: lista)
        {
            String s = words.toString().substring(words.toString().indexOf(" ") + 1);
            String s2 = words.toString().substring(0,words.toString().indexOf(" ")+1);
            getlinkandname(s2,s);


        }
    }

    public void PrevButton(View view) throws ExecutionException, InterruptedException
    {
        bierzacy=bierzacy-1;
        if (bierzacy==0)
            prev.setVisibility(View.INVISIBLE);
        else
            prev.setVisibility(View.VISIBLE);
        if (bierzacy+1==iloscstron)
            next.setVisibility(View.INVISIBLE);
        else
            next.setVisibility(View.VISIBLE);
        ArrayList lista=  new Downloadtaskjsoup().execute(szuk,String.valueOf(bierzacy)).get();
        adapter.clear();

        iloscstron= Integer.valueOf(lista.get(0).toString().substring(lista.get(0).toString().lastIndexOf("=") + 1, lista.get(0).toString().length()));
        textView.setText(String.valueOf(bierzacy + 1) + " / " + String.valueOf(iloscstron));
        lista.remove(0);
        for (Object words: lista)
        {
            String s = words.toString().substring(words.toString().indexOf(" ") + 1);
            String s2 = words.toString().substring(0,words.toString().indexOf(" ")+1);
            getlinkandname(s2,s);

        }
    }



}
