package eetc.com.productivityinsight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import eetc.com.productivityinsight.db.ProductivityInsightContract;
import eetc.com.productivityinsight.db.ProductivityInsightDBHelper;
import eetc.com.productivityinsight.db.User;

public class TestDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);

        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        final ProductivityInsightDBHelper dbHelper = new ProductivityInsightDBHelper(this);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User addUser = new User();
                addUser.setUsername("delicmakaveli");
                addUser.setPassword("gwguru94");
                addUser.setUserID(9);
                dbHelper.addUserToDB(addUser);

                List<User> res = dbHelper.readFromDB();
                for (User user : res) {
                    Log.i("TEST", "Got " + user.toString());
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAll();
            }
            List<User> res = dbHelper.readFromDB();
        });
    }
}
