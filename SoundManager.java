package com.example.diaboloacademia;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

    private MediaPlayer bgmPlayer;
    private Context context;

    public SoundManager(Context context){
        this.context = context;
    }

    // Start background music (looping)
    public void startBgm(int resId){
        stopBgm(); // stop if already playing
        bgmPlayer = MediaPlayer.create(context, resId);
        bgmPlayer.setLooping(true);
        bgmPlayer.start();
    }

    // Stop background music
    public void stopBgm(){
        if(bgmPlayer != null){
            if(bgmPlayer.isPlaying()) bgmPlayer.stop();
            bgmPlayer.release();
            bgmPlayer = null;
        }
    }

    // Play a short sound effect
    public void playSfx(int resId){
        MediaPlayer sfx = MediaPlayer.create(context, resId);
        sfx.start();
        sfx.setOnCompletionListener(MediaPlayer::release);
    }

    // Release all resources
    public void release(){
        stopBgm();
    }
}
