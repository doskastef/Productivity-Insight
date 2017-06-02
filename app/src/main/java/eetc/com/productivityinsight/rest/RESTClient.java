package eetc.com.productivityinsight.rest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eetc.com.productivityinsight.DaysActivity;
import eetc.com.productivityinsight.MainActivity;
import eetc.com.productivityinsight.SignUpActivity;
import eetc.com.productivityinsight.db.ProductivityInsightDBHelper;
import eetc.com.productivityinsight.db.User;

public class RESTClient {
    public static boolean outcome = true;
    private final String url = "http://10.0.2.2:8000/api/"; ///insight/days/4" //localhost emulator
    //private final String url = "http://192.168.1.2:8000/api/"; // localhost device
    //private final String url = "http://ip.jsontest.com/"; // remote host

    public void getDailyProductivity(final Context context, final MainActivity mainActivity) {
        ProductivityInsightDBHelper db = new ProductivityInsightDBHelper(context);
        String dailyProductivityURL = url + "insight/days/";
        List<User> users = db.readFromDB();
        String user_id = null;
        for (User user: users) {
            user_id = Integer.toString(user.getUserID());
        }
        dailyProductivityURL += user_id;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                dailyProductivityURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                Bundle bundle = new Bundle();
                                Log.i("MON_PROD", Integer.toString(response.getInt("mon_prod")));
                                bundle.putInt("mon_prod", response.getInt("mon_prod"));
                                bundle.putInt("tue_prod", response.getInt("tue_prod"));
                                bundle.putInt("wed_prod", response.getInt("wed_prod"));
                                bundle.putInt("thu_prod", response.getInt("thu_prod"));
                                bundle.putInt("fri_prod", response.getInt("fri_prod"));
                                bundle.putInt("sat_prod", response.getInt("sat_prod"));
                                bundle.putInt("sun_prod", response.getInt("sun_prod"));

                                Intent startDays = new Intent(context, DaysActivity.class);
                                startDays.putExtras(bundle);

                                mainActivity.startActivity(startDays);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyError dailyProd()", error.toString());
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        queue.add(request);
    }


    public void signUp(final String email, final String password, final Context context, final SignUpActivity signUpActivity) {

        String signupURL = url + "create_user";

        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            JSONObject jsonObj = new JSONObject(params);

            /*
            // CONVERT JSON OBJECT TO JSON ARRAY
            JSONArray json = new JSONArray();
            json.put(jsonObj);
            */

            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.POST,
                    signupURL,
                    jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.i("JSON response signUp()", response.toString(4));
                                int user_id = response.getInt("id");

                                User user = new User(email, password, user_id);
                                ProductivityInsightDBHelper db = new ProductivityInsightDBHelper(context);
                                Log.i("USER", user.getUsername());
                                Log.i("USER", user.getPassword());
                                Log.i("USER", Integer.toString(user.getUserID()));
                                db.addUserToDB(user);

                                Intent startMain = new Intent(context, MainActivity.class);
                                signUpActivity.startActivity(startMain);
                                signUpActivity.finish();

                                outcome = true;
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse nr = error.networkResponse;
                            if (nr != null && nr.statusCode == 409) {
                                CharSequence msg = "Account with that email already exists!";
                                RESTClient.outcome = false;
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                                t.show();
                            }
                            RESTClient.outcome = false;

                            if (nr != null && nr.statusCode == 201) {
                                CharSequence msg = "Signup Successful!";
                                RESTClient.outcome = true;
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                                t.show();
                            }

                            Log.i("Volley Error signUp()", error.toString());
                        }
                    }
            );

            req.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );

            queue.add(req);
            //return RESTClient.outcome;

        } catch (Exception e) {
            e.printStackTrace();
            RESTClient.outcome = false;
            //return RESTClient.outcome;
        }
    }
}
