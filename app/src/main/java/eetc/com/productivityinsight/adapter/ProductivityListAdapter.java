package eetc.com.productivityinsight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import eetc.com.productivityinsight.R;
import eetc.com.productivityinsight.unit.TimeUnit;

public class ProductivityListAdapter extends ArrayAdapter<TimeUnit> {
    public ProductivityListAdapter(Context context, TimeUnit[] objects) {
        super(context, R.layout.productivity_list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View productivityListItemView = inflater.inflate(R.layout.productivity_list_item, parent, false);

        TimeUnit unit = getItem(position);

        TextView name = (TextView) productivityListItemView.findViewById(R.id.name);
        TextView productivity = (TextView) productivityListItemView.findViewById(R.id.productivity);

        name.setText(unit.getName());
        productivity.setText(unit.getProductivity());
        String prod = unit.getProductivity();
        switch (prod) {
            case "Very productive.":
                productivityListItemView.setBackgroundColor(Color.rgb(0, 255, 0));
                break;
            case "Pretty productive.":
                productivityListItemView.setBackgroundColor(Color.rgb(153, 255, 51));
                break;
            case "Average.":
                productivityListItemView.setBackgroundColor(Color.rgb(255, 255, 0));
                break;
            case "Not very productive.":
                productivityListItemView.setBackgroundColor(Color.rgb(255, 71, 26));
                break;
            case "Not at all productive.":
                productivityListItemView.setBackgroundColor(Color.rgb(255,0,0));
                break;
            case "No data available yet.":
                productivityListItemView.setBackgroundColor(Color.GRAY);
                break;
            default:
                break;
        }

        productivityListItemView.getBackground().setAlpha(169);

        return productivityListItemView;
    }
}
