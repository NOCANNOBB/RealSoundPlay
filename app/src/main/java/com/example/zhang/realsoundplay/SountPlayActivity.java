package com.example.zhang.realsoundplay;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.HashMap;

public class SountPlayActivity extends AppCompatActivity {
    SoundPool mSound;
    HashMap<Integer,Integer> hm;
    int currStreamID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sount_play);
        initSoundPool();
        Button b1 = (Button)findViewById(R.id.btn_play);
        Button b2 = (Button)findViewById(R.id.btn_Stop);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V)
            {
                playSound(1,0);
                Toast.makeText(getBaseContext(),"播放即时音效",Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSound.stop(currStreamID);
                Toast.makeText(getBaseContext(),"停止播放即时音效",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initSoundPool(){
        SoundPool.Builder sb = new SoundPool.Builder();
        sb.setMaxStreams(10);

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);//设置音频流的合适的属性

        sb.setAudioAttributes(attrBuilder.build());
        mSound = sb.build();
        hm = new HashMap<Integer, Integer>();
        hm.put(1,mSound.load(this,R.raw.playsound,1));
    }
    public void playSound(int sound,int loop){
        AudioManager am = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;
        currStreamID = mSound.play(hm.get(sound),volume,volume,1,loop,1.0f);
    }
}
