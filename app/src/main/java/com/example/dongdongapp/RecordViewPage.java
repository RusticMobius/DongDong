package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import java.net.URI;

public class RecordViewPage extends AppCompatActivity {

    private VideoView vvVideo;
    private ImageButton retButton;
    private ImageButton playButton;
    private TextView rankTextView;
    private TextView adviceTextView;
    // TODO
    private String videoPath = "https://vd2.bdstatic.com/mda-mafn1sffpj7cjnrw/v1-cae/sc/mda-mafn1sffpj7cjnrw.mp4?v_from_s=hkapp-haokan-suzhou&auth_key=1668529191-0-0-0e03b9468bc10c255c665fea1544172c&bcevod_channel=searchbox_feed&pd=1&cd=0&pt=3&logid=2990968645&vid=10737651655157153533&abtest=104959_2-105568_2&klogid=2990968645";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view_page);

        vvVideo = findViewById(R.id.recordVideoView);
        retButton = findViewById(R.id.retButton);
        playButton = findViewById(R.id.playButton);
        rankTextView = findViewById(R.id.ratingTextView);
        adviceTextView = findViewById(R.id.recordAdvice);
        initButtonListener();
        initVideoView();

    }

    private void initButtonListener(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
        }
      });
      playButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(vvVideo.isPlaying()){
            playButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_48);
            vvVideo.pause();
          } else {
            playButton.setImageResource(R.drawable.ic_baseline_play_circle_outline_blue_48);
            vvVideo.start();
          }
        }
      });
    }

    private void initVideoView(){
      vvVideo.setVideoURI(Uri.parse(videoPath));
      vvVideo.start();
    }
}
