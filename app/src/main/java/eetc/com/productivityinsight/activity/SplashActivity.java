package eetc.com.productivityinsight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import eetc.com.productivityinsight.activity.MainActivity;
import eetc.com.productivityinsight.activity.SignUpActivity;
import eetc.com.productivityinsight.db.ProductivityInsightDBHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("START", " Started Splash Activity.");
        try {
            TimeUnit.SECONDS.sleep((long)4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final ProductivityInsightDBHelper dbHelper = new ProductivityInsightDBHelper(this);
        if (dbHelper.isDBEmpty()) {
            Intent startSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
            Log.i("DB", "Database is empty! Starting SignUpActivity...");
            startActivity(startSignUp);
            finish();
        } else {
            Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
            Log.i("DB", "Database is not empty! Starting MainActivity...");
            startActivity(startMain);
            finish();
        }
    }
}
