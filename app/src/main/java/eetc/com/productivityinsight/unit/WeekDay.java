package eetc.com.productivityinsight.unit;

import android.util.Log;

public class WeekDay extends TimeUnit {
    private String name;
    private String productivity;

    public WeekDay() {
        super();
    }

    public WeekDay(String name, int productivity_score) {
        this.name = name;
        if (productivity_score > 3) {
            this.productivity = "Very productive.";
        } else if (productivity_score > 1) {
            this.productivity = "Pretty productive.";
        } else if (productivity_score >= -1) {
            this.productivity = "Average.";
        } else if (productivity_score > -4) {
            this.productivity = "Not very productive.";
        } else {
            Log.i("PROD", Integer.toString(productivity_score));
            this.productivity = "Not at all productive.";
        }

        if (productivity_score == -144) {
            this.productivity = "No data available yet.";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductivity() {
        return productivity;
    }

    public void setProductivity(String productivity) {
        this.productivity = productivity;
    }
}
