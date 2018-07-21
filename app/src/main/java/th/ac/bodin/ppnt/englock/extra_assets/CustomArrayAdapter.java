package th.ac.bodin.ppnt.englock.extra_assets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import th.ac.bodin.ppnt.englock.R;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public CustomArrayAdapter(Context context, String[] values) {
        super(context, R.layout.list_settings, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_settings, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        if (s.equals("Settings")) {
            imageView.setImageResource(R.drawable.ic_settings_black_24dp);
        } else if (s.equals("Help")) {
            imageView.setImageResource(R.drawable.ic_help_black_24dp);
        } else {
            imageView.setImageResource(R.drawable.ic_more_black_24dp);
        }

        return rowView;
    }
}
