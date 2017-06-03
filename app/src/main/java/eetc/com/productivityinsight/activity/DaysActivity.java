package eetc.com.productivityinsight.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eetc.com.productivityinsight.R;
import eetc.com.productivityinsight.adapter.ProductivityListAdapter;
import eetc.com.productivityinsight.unit.TimeUnit;

public class DaysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        Bundle bundle = getIntent().getExtras();
        /*
        CharSequence msg = "MON " + bundle.get("mon_prod").toString();
        Toast t = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        t.show();
        */

        TimeUnit[] days = new TimeUnit[7];
        if (bundle != null) {
            for (String key: bundle.keySet()) {
                int prod = bundle.getInt(key);
                TimeUnit unit;
                switch (key) {
                    case "mon_prod":
                        unit = new TimeUnit("Monday", prod);
                        days[0] = unit;
                        break;
                    case "tue_prod":
                        unit = new TimeUnit("Tuesday", prod);
                        days[1] = unit;
                        break;
                    case "wed_prod":
                        unit = new TimeUnit("Wednesday", prod);
                        days[2] = unit;
                        break;
                    case "thu_prod":
                        unit = new TimeUnit("Thursday", prod);
                        days[3] = unit;
                        break;
                    case "fri_prod":
                        unit = new TimeUnit("Friday", prod);
                        days[4] = unit;
                        break;
                    case "sat_prod":
                        unit = new TimeUnit("Saturday", prod);
                        days[5] = unit;
                        break;
                    case "sun_prod":
                        unit = new TimeUnit("Sunday", prod);
                        days[6] = unit;
                        break;

                }
            }
        }
        /*
        ListAdapter daysAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                days
        );
        */

        ProductivityListAdapter daysAdapter = new ProductivityListAdapter(getApplicationContext(), days);
        ListView daysListView = (ListView) findViewById(R.id.days_list_view);
        daysListView.setAdapter(daysAdapter);

    }
}
