package eetc.com.productivityinsight.db;

import android.provider.BaseColumns;

public final class ProductivityInsightContract {
    private ProductivityInsightContract() {};

    public static class ProductivityInsightEntry implements BaseColumns {
        public static final String TABLE_NAME = "productivity_insight_data";
        //public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USERNAME = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_USER_ID = "user_id";
    }
}
