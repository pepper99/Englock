package th.ac.bodin.ppnt.englock.extra_assets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import th.ac.bodin.ppnt.englock.R;

public class CustomAdapter extends ArrayAdapter<Product> {
    public CustomAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        Product product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        ImageView tick = (ImageView) v.findViewById(R.id.checker);
        ImageView pt = (ImageView) v.findViewById(R.id.monnay);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);

        img.setImageResource(product.getImageId());
        txtTitle.setText(product.getTitle());
        txtDescription.setText(product.getDescription());
        tick.setVisibility(View.INVISIBLE);

        if(product.isBought()) {
            tick.setVisibility(View.VISIBLE);
            txtDescription.setText("Bought");
            pt.setVisibility(View.GONE);
        }

        if(position == 0) {
            txtDescription.setText("Default");
            pt.setVisibility(View.GONE);
        }

        if (product.isSelected()) {
            LinearLayout layout =(LinearLayout) v.findViewById(R.id.grid_item);
            layout.setBackgroundResource(R.drawable.gradient03);
            txtDescription.setText("Selected");
            pt.setVisibility(View.GONE);
        }

        return v;
    }
}
