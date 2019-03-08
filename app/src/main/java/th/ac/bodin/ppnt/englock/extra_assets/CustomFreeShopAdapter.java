package th.ac.bodin.ppnt.englock.extra_assets;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class CustomFreeShopAdapter extends ArrayAdapter<FreeShopProduct> {
    public CustomFreeShopAdapter(Context context, int resource, List<FreeShopProduct> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.freeshop_griditem, null);
        }
        FreeShopProduct product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.freeshop_itemimg);
        TextView txtTitle = (TextView) v.findViewById(R.id.freeshop_itemtitle);

        Picasso.get()
                .load(product.getImageId())
                .into(img);
        txtTitle.setText(product.getTitle());

        return v;
    }
}
