package eetc.com.productivityinsight.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import eetc.com.productivityinsight.db.ProductivityInsightDBHelper;
import eetc.com.productivityinsight.db.User;
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
        calendar.set(Calendar.MINUTE, 17);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        long systemTime = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
        if (systemTime > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DATE, 1);
            Log.i("NOTIFICATION", "Delayed notification to next day");
        }
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );

        TextView loggedInAs = (TextView) findViewById(R.id.logged_in_as_txt);
        final ProductivityInsightDBHelper dbHelper = new ProductivityInsightDBHelper(getApplicationContext());
        List<User> res = dbHelper.readFromDB();
        for (User user : res) {
            String str = "Logged in as: " + user.getUsername();
            loggedInAs.setText(str);
        }

        TextView logOut = (TextView) findViewById(R.id.logout_link_txt);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogOut = new Intent(getApplicationContext(), LoginActivity.class);
                dbHelper.deleteAll();
                startActivity(startLogOut);
                finish();
            }
        });
    }
}
