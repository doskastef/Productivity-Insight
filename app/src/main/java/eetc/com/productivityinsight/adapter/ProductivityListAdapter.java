package eetc.com.productivityinsight.adapter;

import android.content.Context;
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

        return productivityListItemView;
    }
}
