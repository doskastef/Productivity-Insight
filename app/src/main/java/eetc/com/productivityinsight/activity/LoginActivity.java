package eetc.com.productivityinsight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import eetc.com.productivityinsight.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO DO SAME ROUTINE AS SIGNUP, BUT LOOK FOR ALREADY EXISTS RESPONSE STATUS CODE

        TextView loginTextView = (TextView) findViewById(R.id.signup_link_txt);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(startSignUp);
                finish();
            }
        });

    }
}
