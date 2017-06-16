package eetc.com.productivityinsight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import eetc.com.productivityinsight.R;
import eetc.com.productivityinsight.rest.RESTClient;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Log.i("START", " Started SignUp Activity.");

        final EditText emailField = (EditText) findViewById(R.id.email_field);
        final EditText passwordField = (EditText) findViewById(R.id.password_field);
        Button signupButton = (Button) findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RESTClient client = new RESTClient();

                    client.signUp(emailField.getText().toString(),
                            Base64.encodeToString(passwordField.getText().toString().getBytes("UTF-8"), Base64.DEFAULT),
                            getApplicationContext(),
                            SignUpActivity.this
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TextView loginTextView = (TextView) findViewById(R.id.login_link_txt);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startLogin);
                finish();
            }
        });


        Button cheat = (Button) findViewById(R.id.cheat);
        cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startMain);
            }
        });
    }
}
