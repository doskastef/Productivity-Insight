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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import eetc.com.productivityinsight.DaysActivity;
import eetc.com.productivityinsight.MainActivity;
import eetc.com.productivityinsight.MonthsActivity;
import eetc.com.productivityinsight.PollActivity;
import eetc.com.productivityinsight.SignUpActivity;
import eetc.com.productivityinsight.db.ProductivityInsightDBHelper;
import eetc.com.productivityinsight.db.User;

public class RESTClient {
    private final String url = "http://10.0.2.2:8000/api/"; ///insight/days/4" //localhost emulator
    //private final String url = "http://192.168.1.2:8000/api/"; // localhost device
    //private final String url = "http://ip.jsontest.com/"; // remote host

    public void postResult(final Context context, final PollActivity pollActivity, int result) {
        ProductivityInsightDBHelper db = new ProductivityInsightDBHelper(context);
        String postResultURL = url + "result/days";

        List<User> users = db.readFromDB();
        String email = null;
        String password = null;
        String user_id = null;
        for (User user: users) {
            user_id = Integer.toString(user.getUserID());
            email = user.getUsername();
            password = user.getPassword();
        }
        String dayName = null;
        String monthName = null;

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                dayName = "monday";
                break;
            case Calendar.TUESDAY:
                dayName = "tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayName = "wednesday";
                break;
            case Calendar.THURSDAY:
                dayName = "thursday";
                break;
            case Calendar.FRIDAY:
                dayName = "friday";
                break;
            case Calendar.SATURDAY:
                dayName = "saturday";
                break;
            case Calendar.SUNDAY:
                dayName = "sunday";
                break;
            default:
                break;
        }
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
                monthName = "jan";
                break;
            case Calendar.FEBRUARY:
                monthName = "feb";
                break;
            case Calendar.MARCH:
                monthName = "mar";
                break;
            case Calendar.APRIL:
                monthName = "apr";
                break;
            case Calendar.MAY:
                monthName = "may";
                break;
            case Calendar.JUNE:
                monthName = "jun";
                break;
            case Calendar.JULY:
                monthName = "jul";
                break;
            case Calendar.AUGUST:
                monthName = "aug";
                break;
            case Calendar.SEPTEMBER:
                monthName = "sep";
                break;
            case Calendar.OCTOBER:
                monthName = "oct";
                break;
            case Calendar.NOVEMBER:
                monthName = "nov";
                break;
            case Calendar.DECEMBER:
                monthName = "dec";
                break;
            default:
                break;
        }

        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            params.put("user_id", user_id);
            params.put("week_day", dayName);
            params.put("month", monthName);
            params.put("result", Integer.toString(result));

            JSONObject jsonObj = new JSONObject(params);

            /*
            // CONVERT JSON OBJECT TO JSON ARRAY
            JSONArray json = new JSONArray();
            json.put(jsonObj);
            */

            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.POST,
                    postResultURL,
                    jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.i("JSON response postRes()", response.toString(4));
                                pollActivity.finish();
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse nr = error.networkResponse;
                            if (nr != null && nr.statusCode == 422) {
                                CharSequence msg = "Unprocessable entity";
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                                t.show();
                            }
                            if (nr != null && nr.statusCode == 404) {
                                CharSequence msg = "User not found";
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                                t.show();
                            }
                            if (nr != null && nr.statusCode == 400) {
                                CharSequence msg = "Bad Request";
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                                t.show();
                            }
                            if (nr != null && nr.statusCode == 201) {
                                CharSequence msg = "Updated productivity results.";
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMonthlyProductivity(final Context context, final MainActivity mainActivity) {
        ProductivityInsightDBHelper db = new ProductivityInsightDBHelper(context);
        String dailyProductivityURL = url + "insight/months/";
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
                            Log.i("JUN_PROD", Integer.toString(response.getInt("jun_prod")));
                            bundle.putInt("jan", response.getInt("jan_prod"));
                            bundle.putInt("feb", response.getInt("feb_prod"));
                            bundle.putInt("mar", response.getInt("mar_prod"));
                            bundle.putInt("apr", response.getInt("apr_prod"));
                            bundle.putInt("may", response.getInt("may_prod"));
                            bundle.putInt("jun", response.getInt("jun_prod"));
                            bundle.putInt("jul", response.getInt("jul_prod"));
                            bundle.putInt("aug", response.getInt("aug_prod"));
                            bundle.putInt("sep", response.getInt("sep_prod"));
                            bundle.putInt("oct", response.getInt("oct_prod"));
                            bundle.putInt("nov", response.getInt("nov_prod"));
                            bundle.putInt("dec", response.getInt("dec_prod"));

                            Intent startMonths = new Intent(context, MonthsActivity.class);
                            startMonths.putExtras(bundle);

                            mainActivity.startActivity(startMonths);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyError montProd()", error.toString());
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        queue.add(request);
    }

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
                                Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                                t.show();
                            }

                            if (nr != null && nr.statusCode == 201) {
                                CharSequence msg = "Signup Successful!";
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
            //return RESTClient.outcome;
        }
    }
}
