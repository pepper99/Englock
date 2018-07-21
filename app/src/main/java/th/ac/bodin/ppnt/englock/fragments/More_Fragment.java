package th.ac.bodin.ppnt.englock.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import th.ac.bodin.ppnt.englock.LockscreenBeforeActivity;
import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.R;
import th.ac.bodin.ppnt.englock.SettingsPop;
import th.ac.bodin.ppnt.englock.WelcomeActivity;
import th.ac.bodin.ppnt.englock.extra_assets.AboutDialog;
import th.ac.bodin.ppnt.englock.extra_assets.CustomArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class More_Fragment extends ListFragment {

    private String[] items;

    public static More_Fragment newInstance() {
        return new More_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        items = getActivity().getResources().getStringArray(R.array.settingarray);

        setListAdapter(new CustomArrayAdapter(this.getActivity(), items));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        switch (position) {
            case 0:
                openSettings();
                break;
            case 1:
                Intro();
                break;
            case 2:
                aus();
                break;
        }
    }

    private void aus() {
        String desc = "Developed by\n" +
                "\t• Poptum Charoennaew (Programmer)\n" +
                "\t• Nattapat Panchavinin (Assistant)\n" +
                "\t• Lattapol Dansakul (Advisor)\n" +
                "\tM.6/14 Bodindecha (Sing Singhaseni)\n" +
                "\n" +
                "พัฒนาโดย\n" +
                "\t• นายภพธรรม เจริญแนว (โปรแกรมเมอร์)\n" +
                "\t• นายณัฐภัทร ปัญจวีณิน (ผู้ช่วย)\n" +
                "\t• คุณครูลัทธพล ด่านสกุล (ครูที่ปรึกษา)\n" +
                "\tม.6/14 โรงเรียนบดินทรเดชา (สิงห์ สิงหเสนี)";
        AboutDialog alert = new AboutDialog.Builder()
                .setMessage(desc)
                .setButton(R.string.ok)
                .setLayout(R.layout.about_box)
                .build();
        alert.show(getActivity().getFragmentManager(), "LUL");
    }

    public void openSettings() {
        Intent i = new Intent(getContext(), SettingsPop.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void Intro() {
        Intent i = new Intent(this.getContext(), WelcomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
