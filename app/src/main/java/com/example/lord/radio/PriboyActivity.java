package com.example.lord.radio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PriboyActivity extends AppCompatActivity implements ClickListener, View.OnClickListener {

    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerview;
    private ToggleButton playPause;
    private ToggleButton stopButton;
    private MyAdapter mAdapter;
    //private LinearLayout main;
    // private ImageView imageView;
    //private Button playpause;
    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer;
    private String STREAM_URL = "http://stream6.radiostyle.ru:8006/priboyfm";
    private String STREAM_URL1 = "http://stream3.radiostyle.ru:8003/radioacca" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        // imageView = (ImageView) findViewById(R.id.imageView);
        playPause = (ToggleButton) findViewById(R.id.playpause);
        mAdapter = new MyAdapter(itemList);
        mAdapter.setClickListener(this);
        stopButton = (ToggleButton) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        prepareItem();
        playPause.setOnClickListener(this);
    }
    private void prepareItem() {
        Item item = new Item(R.drawable.a1,"Радио Прибой","100.7 FM");
        itemList.add(item);
        item = new Item(R.drawable.sdfsd_198x99,"Радио Асса","104.8 FM");
        itemList.add(item);
        //mAdapter.notifyDataSetChanged();
    }
    @Override
    public void itemClicked(View view, int position) {
        releaseMP();
        if(position==0) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(STREAM_URL);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
        else if (position == 1){
            mediaPlayer = new MediaPlayer();
            try{
                mediaPlayer.reset();
                mediaPlayer.setDataSource(STREAM_URL1);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            } catch (IOException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            System.out.println("position...." +position);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playpause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
                break;
            case R.id.stopButton:
                mediaPlayer.stop();
        }

    }
    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
