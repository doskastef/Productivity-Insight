package eetc.com.productivityinsight.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import eetc.com.productivityinsight.notification.NotificationReceiver;
import eetc.com.productivityinsight.R;
import eetc.com.productivityinsight.rest.RESTClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dailyButton = (Button) findViewById(R.id.daily_button);
        Button monthlyButton = (Button) findViewById(R.id.monthly_button);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESTClient restClient = new RESTClient();
                restClient.getDailyProductivity(getApplicationContext(), MainActivity.this);
            }
        });

        monthlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESTClient restClient = new RESTClient();
                restClient.getMonthlyProductivity(getApplicationContext(), MainActivity.this);
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 07);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        long systemTime = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
        if (systemTime >= calendar.getTimeInMillis()) {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );

        //TODO ADD LOGOUT OPTION THAT DELETES ALL USERS FROM DATABASE
        // database can have only one entry at any moment
        //TODO ADD LOGIN OPTION THAT HANDLES 409 REQUEST BY WRITING USER TO DATABASE
        //LOGIN IS SAME AS SIGNUP EXCEPT 409 IS THE ONLY ACCEPTABLE RESPONSE
    }
}
