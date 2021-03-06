package eetc.com.productivityinsight.unit;

import android.util.Log;

public class Month extends TimeUnit {
    private String name;
    private String productivity;

    public Month() {
        super();
    }

    public Month(String name, int productivity_score) {
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
            this.productivity = "Not at all productive.";
        }

        if (productivity_score == -144) {
            Log.i("TEST", "PRC");
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
