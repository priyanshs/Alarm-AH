package com.example.ahalarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;

import java.util.Calendar;

// TODO: Tracking on foot
// TODO: Managing the voice feed back and DO-NOT-DISTURB
// TODO: Double or Nothing with a cap.


public class MainActivity extends AppCompatActivity {
    // init all the XML variables.
    AlarmManager alarmManager;
    private PendingIntent pending_intent;
    private TimePicker alarmTimePicker;
    private TextView alarmTextView;
    private AlarmReceiver alarm;
    public Calendar calendar = Calendar.getInstance();

    MainActivity inst;
    Context context;

    protected void onCreate(Bundle savedInstanceState) {
        // Save state incase of app hibernation.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.context = this;

        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.alarmText);

        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        // Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // set the alarm to the time that you picked


        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);


        // Click action for the Set alarm button.
        Button start_alarm= (Button) findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            @Override
            public void onClick(View v) {

                calendar.add(Calendar.SECOND, 3);
                final int hour = alarmTimePicker.getHour();
                final int minute = alarmTimePicker.getMinute();;

                Log.e("MyActivity", "In the receiver with " + hour + " and " + minute);
                setAlarmText("You clicked a " + hour + " and " + minute);

                // Gets the exact time from the TimePicker module converts to milliseconds and passes to alarm manager.
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                myIntent.putExtra("extra", "yes");
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(),
                        pending_intent), pending_intent);

                setAlarmText("Alarm set to " + hour + ":" + minute);
            }

        });

        // Click action for the stop alarm button.
        Button stop_alarm= (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int min = 1;
                int max = 9;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                // Puts the Extra value and broadcasts to AlarmReceiver.
                myIntent.putExtra("extra", "no");
                sendBroadcast(myIntent);

                // Cancels the running alarm.
                alarmManager.cancel(pending_intent);
                setAlarmText("Alarm canceled");

            }
        });

    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }



    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }

}
