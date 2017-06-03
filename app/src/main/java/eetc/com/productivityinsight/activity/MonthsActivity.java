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

public class MonthsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);

        Bundle bundle = getIntent().getExtras();

        //List<TimeUnit> months = new ArrayList<>();
        TimeUnit[] months = new TimeUnit[12];

        if (bundle != null) {
            for (String key: bundle.keySet()) {
                int prod = bundle.getInt(key);
                TimeUnit unit = null;
                switch (key) {
                    case "jan":
                        unit = new TimeUnit("January", prod);
                        months[0] = unit;
                        break;
                    case "feb":
                        unit = new TimeUnit("February", prod);
                        months[1] = unit;
                        break;
                    case "mar":
                        unit = new TimeUnit("March", prod);
                        months[2] = unit;
                        break;
                    case "apr":
                        unit = new TimeUnit("April", prod);
                        months[3] = unit;
                        break;
                    case "may":
                        unit = new TimeUnit("May", prod);
                        months[4] = unit;
                        break;
                    case "jun":
                        unit = new TimeUnit("June", prod);
                        months[5] = unit;
                        break;
                    case "jul":
                        unit = new TimeUnit("July", prod);
                        months[6] = unit;
                        break;
                    case "aug":
                        unit = new TimeUnit("August", prod);
                        months[7] = unit;
                        break;
                    case "sep":
                        unit = new TimeUnit("September", prod);
                        months[8] = unit;
                        break;
                    case "oct":
                        unit = new TimeUnit("October", prod);
                        months[9] = unit;
                        break;
                    case "nov":
                        unit = new TimeUnit("November", prod);
                        months[10] = unit;
                        break;
                    case "dec":
                        unit = new TimeUnit("December", prod);
                        months[11] = unit;
                        break;
                }
                //months.add(unit);
            }
        }

        ProductivityListAdapter monthsAdapter = new ProductivityListAdapter(getApplicationContext(), months);
        /*
        ListAdapter monthsAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                months
        );
        */

        ListView monthsListView = (ListView) findViewById(R.id.months_list_view);
        monthsListView.setAdapter(monthsAdapter);

        /*
        CharSequence msg = "JUN " + bundle.get("jun").toString();
        Toast t = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        t.show();
        */
    }
}
