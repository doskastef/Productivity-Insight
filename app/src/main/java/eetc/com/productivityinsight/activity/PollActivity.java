package eetc.com.productivityinsight.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import eetc.com.productivityinsight.R;
import eetc.com.productivityinsight.rest.RESTClient;

public class PollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setBackgroundColor(Color.WHITE);
        radioGroup.getBackground().setAlpha(100);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int result = 1;
                switch (checkedId) {
                    case R.id.radio_v:
                        result = 5;
                        break;
                    case R.id.radio_p:
                        result = 3;
                        break;
                    case R.id.radio_avg:
                        result = 1;
                        break;
                    case R.id.radio_nv:
                        result = -3;
                        break;
                    case R.id.radio_naa:
                        result = -5;
                        break;
                    default:
                        break;
                }
                RESTClient restClient = new RESTClient();
                restClient.postResult(getApplicationContext(), PollActivity.this, result);
                Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startMain);
                finish();
            }
        });
    }
}
