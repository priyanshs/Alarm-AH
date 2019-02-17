package com.example.ahalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
/// Stays in the bckground waiting for the time time to sound the alarm.
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        // Extra is used to check if the state is set for on. Does not pass directly from  main.
        // Pass directly for off state.
        Log.e("MyActivity", "In the receiver with " + state);
        // Calls the RingtonePlaying service for sound.
        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);

        context.startService(serviceIntent);
    }
}
