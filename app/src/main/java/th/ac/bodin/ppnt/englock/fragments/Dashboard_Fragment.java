package th.ac.bodin.ppnt.englock.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.w3c.dom.Text;

import th.ac.bodin.ppnt.englock.R;
import th.ac.bodin.ppnt.englock.stats.DashboardStats;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Fragment extends Fragment {

    DecoView arcView;
    TextView percentage1, desc;
    int series1Index;
    SeriesItem seriesItem1;

    int seen;
    int correct0;
    boolean isZero = false;

    String temp;
    String format = "%.1f";

    public static Dashboard_Fragment newInstance() {
        return new Dashboard_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_dashboard_, container, false);
    }

    @Override
    public void onResume() {

        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDuration(1000)
                .build());
        arcView.addEvent(new DecoEvent.Builder(correct0).setIndex(series1Index).setDelay(400).build());

        updateUI();
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences shared = getActivity().getSharedPreferences("Englock Database", Context.MODE_PRIVATE);

        seen = shared.getInt("count", 0);
        correct0 = shared.getInt("correct0", 0);

        arcView = (DecoView)getView().findViewById(R.id.ArcView);
        arcView.configureAngles(330, 0);

        percentage1 = (TextView) getView().findViewById(R.id.percentage1);
        desc = (TextView) getView().findViewById(R.id.desc);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){
            TextViewCompat.setAutoSizeTextTypeWithDefaults(desc, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }

        temp = getResources().getString(R.string.statswheel);
        desc.setText(temp + " 0/" + String.valueOf(seen) + ")");

        if(seen == 0){
            seen = 1;
            isZero = true;
        }

        // Create background track
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 200, 200, 200))
                .setRange(0, seen, seen)
                .setInitialVisibility(false)
                .setLineWidth(62f)
                .build());

        seriesItem1 = new SeriesItem.Builder(Color.argb(255, 170, 255, 128))
                .setRange(0, seen, 0)
                .setInitialVisibility(false)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_OUTER, Color.parseColor("#22000000"), 0.3f))
                .setLineWidth(62f)
                .build();

        series1Index = arcView.addSeries(seriesItem1);

        seriesItem1.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                percentage1.setText(String.format(format,currentPosition/seen*100)+"%");
                if(!isZero) desc.setText(temp + " " + String.format("%.0f",currentPosition) + "/" + String.valueOf(seen) + ")");
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDuration(300)
                .build());
        arcView.addEvent(new DecoEvent.Builder(correct0).setIndex(series1Index).setDelay(400).build());

        updateUI();

    }

    private void updateUI(){
        SharedPreferences shared = getActivity().getSharedPreferences("Englock Database", Context.MODE_PRIVATE);
        String[] temp = new String[3];
        String txt;
        for(int i = 0; i < 3; i++) temp[i] = shared.getString("Recent" + String.valueOf(i), " ");
        txt = "• " + temp[0] + "\n• " + temp[1] + "\n• " + temp[2];
        TextView mTextMessage = (TextView)getView().findViewById(R.id.recenttxt);
        mTextMessage.setText(txt);
    }
}
