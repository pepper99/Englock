package th.ac.bodin.ppnt.englock.extra_assets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import th.ac.bodin.ppnt.englock.R;

public class CustomAdapter extends ArrayAdapter<Product> {

    boolean[] loaded;

    public CustomAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        Log.d("kuy",String.valueOf(objects.size()));
        //loaded = new boolean[objects.size()];
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_item, null);

        Product product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        ImageView tick = (ImageView) v.findViewById(R.id.checker);
        ImageView pt = (ImageView) v.findViewById(R.id.monnay);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);

        txtTitle.setText(product.getTitle());

        Picasso.get()
                .load(product.getImageId())
                .into(img);

        if(product.isBought()) {
            LinearLayout layout =(LinearLayout) v.findViewById(R.id.grid_item);
            layout.setBackgroundResource(R.drawable.selectedbox);
            pt.setVisibility(View.GONE);
            Log.d("kuy", product.getTitle() + " isBought");

            if (product.isSelected()) {
                txtDescription.setText(R.string.selected);
                tick.setVisibility(View.VISIBLE);
                Log.d("kuy", product.getTitle() + " isSelected");
            }

            else txtDescription.setText(R.string.purchased);
        }

        else {
            txtDescription.setText(product.getDescription());
        }

        return v;
    }
}
