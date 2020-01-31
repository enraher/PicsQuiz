package com.pics.quiz.other;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.pics.quiz.R;

/**
 * Created by enrasoft on 24/1/20.
 */

public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
       public BackgroundSoundService getService() {
            return BackgroundSoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        startPlayer();
        return mBinder;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    public void onStart(Intent intent, int startId) {

    }

    public IBinder onUnBind(Intent intent) {
        return mBinder;
    }

    public void onStop(){
        stopPlayer();
    }

    public void onPause(){
        player.stop();
        player.release();
    }

    public void onDestroy(){
        stopPlayer();
    }

    public void startPlayer() {
        try {
            player = new MediaPlayer();
            player.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.menu_music));
            player.setLooping(true); // Set looping
            player.setVolume(80, 80);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            player.prepareAsync();
        } catch (Exception e) {
            ReportManager.Companion.getInstance(this).crashReport(e);
            Log.e("BackgroundSoundService", "ERROR");
            if(e.getLocalizedMessage() != null) {
                Log.e("BackgroundSoundService", e.getLocalizedMessage());
            }
        }
    }

    public void stopPlayer() {
        try {
            if (player != null && player.isPlaying()) {
                player.stop();
                player.release();
            }
        } catch (Exception e) {
            ReportManager.Companion.getInstance(this).crashReport(e);
        }
    }
}