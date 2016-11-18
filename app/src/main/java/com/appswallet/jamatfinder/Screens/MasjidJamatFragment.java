package com.appswallet.jamatfinder.Screens;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.Utils.Const;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;

import java.util.Calendar;

import static android.R.attr.tag;

/**
 * Created by android on 11/17/16.
 */
public class MasjidJamatFragment extends Fragment implements BottomSheetTimePickerDialog.OnTimeSetListener {

    private TextView fajarTime;
    private TextView zuharTime;
    private TextView asarTime;
    private TextView magribTime;
    private TextView ishaTime;
    private java.util.Calendar now;
    private FragmentManager fm;
    private Context mContext;
    private GridTimePickerDialog grid;
    private AppCompatActivity mActivity;
    private int tag = 0;
    private TransferTime transferTime;
    private String timeFajar;
    private String timezuhar;
    private String timeAsar;
    private String timeMagrib;
    private String timeIsha;
    private EditText masjidName;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
        transferTime = (TransferTime) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addmasjid, container, false);
        initView(view);

        startListner();
        return view;
    }


    private void initView(View view) {
        fm = getActivity().getFragmentManager();
        mContext = getActivity().getApplicationContext();

        fajarTime = (TextView) view.findViewById(R.id.fajar_time);
        zuharTime = (TextView) view.findViewById(R.id.zuhar_time);
        asarTime = (TextView) view.findViewById(R.id.asar_time);
        magribTime = (TextView) view.findViewById(R.id.magrib_time);
        ishaTime = (TextView) view.findViewById(R.id.isha_time);
        masjidName = (EditText) view.findViewById(R.id.masjid_name);
        now = Calendar.getInstance();


    }


    private void startListner() {
        fajarTime.setOnClickListener(new TimerButtons());
        zuharTime.setOnClickListener(new TimerButtons());
        asarTime.setOnClickListener(new TimerButtons());
        magribTime.setOnClickListener(new TimerButtons());
        ishaTime.setOnClickListener(new TimerButtons());


    }


    public void getData()
    {

    }

    private class TimerButtons implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.fajar_time:
                    tag =1;
                    initDialog("fajar");
                    break;
                case R.id.zuhar_time:
                    tag =2;
                    initDialog("zuhar");
                    break;
                case R.id.asar_time:
                    tag =3;
                    initDialog("zuhar");
                    break;
                case R.id.magrib_time:
                    tag =4;
                    initDialog("zuhar");
                    break;
                case R.id.isha_time:
                    tag =5;
                    initDialog("zuhar");
                    break;
            }

        }
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
          switch (tag)
          {
              case 1:
                timeFajar= setTime(fajarTime,hourOfDay,minute);
                  break;
              case 2:
                  timezuhar = setTime(zuharTime,hourOfDay,minute);
                  break;
              case 3:
                  timeAsar = setTime(asarTime,hourOfDay,minute);
                  break;
              case 4:
                  timeMagrib =setTime(magribTime,hourOfDay,minute);
                  break;
              case 5:
                  timeIsha = setTime(ishaTime,hourOfDay,minute);
                  break;

          }





    }

    private String setTime(TextView timeView, int hourOfDay, int minute) {

        now.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(java.util.Calendar.MINUTE, minute);
       String time = DateFormat.getTimeFormat(getActivity()).format(now.getTime());
        timeView.setText(":  Time set: " + time);
    return  time;
    }

    private void initDialog(String tag) {
                grid = GridTimePickerDialog.newInstance(this,
                now.get(java.util.Calendar.HOUR_OF_DAY),
                now.get(java.util.Calendar.MINUTE),
                DateFormat.is24HourFormat(mContext));
                grid.show(mActivity.getSupportFragmentManager(),tag);
    }


    public void passData()
    {
        transferTime.passTime(timeFajar,timezuhar,timeAsar,timeIsha,timeMagrib,masjidName.getText().toString());
    }

    public interface TransferTime
    {
        public void passTime(String fajar,String zuhar,String asar,String magrib,String isha,String masjidName);
    }
}
