package com.example.ahalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.WindowManager;

import java.util.Random;
// TODO:  Ringtone repeat until end.

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }

// TODO : The notifications don't work. Fix it.

    @RequiresApi(api = 23)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
     /*

        WRITE A NEW SECTION TO MAKE NOTIFICATIONS. ALSO HANDLE THE NOTIFICATIONS PROPERLY.
        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Richard Dawkins is talking" + "!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .build();

*/
        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            int min = 1;
            int max = 6;

            Random r = new Random();
            int random_number = r.nextInt(max - min + 1) + min;
            Log.e("random number is ", String.valueOf(random_number));

            if (random_number == 1) {

                mMediaPlayer = MediaPlayer.create(this, R.raw.a1);
            }
            else if (random_number == 2) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a2);
            }
            else if (random_number == 3) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a3);
            }
            else if (random_number == 4) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a4);
            }
            else if (random_number == 5) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a5);
            }
            else if (random_number == 6) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a6);
            }
            else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a1);
            }
/*
TODO : The app cannot change the volume levels, and cannot wake up the screen. Fix that!
            float volume = (float) 1.0;
            mMediaPlayer.setVolume(volume,volume);
            mMediaPlayer.setWakeMode(this, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

*/
            try{
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }
            catch (Exception e)
            {
                Log.e("MediaPlayer", e.getMessage());
            }

// Notification statement.
//            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
// removing the garbage memory.  
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }


}
