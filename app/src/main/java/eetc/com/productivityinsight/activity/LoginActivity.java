package eetc.com.productivityinsight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import eetc.com.productivityinsight.R;
import eetc.com.productivityinsight.rest.RESTClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i("START", " Started SignUp Activity.");

        final EditText emailField = (EditText) findViewById(R.id.email_field1);
        final EditText passwordField = (EditText) findViewById(R.id.password_field1);
        Button signupButton = (Button) findViewById(R.id.login_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RESTClient client = new RESTClient();

                    client.logIn(emailField.getText().toString(),
                            Base64.encodeToString(passwordField.getText().toString().getBytes("UTF-8"), Base64.DEFAULT),
                            getApplicationContext(),
                            LoginActivity.this
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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
