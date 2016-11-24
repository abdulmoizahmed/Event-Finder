package com.appswallet.jamatfinder.Screens;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appswallet.jamatfinder.FireBase.FirebaseReferences;
import com.appswallet.jamatfinder.FireBase.MasjidModel;
import com.appswallet.jamatfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;

import java.util.Calendar;

/**
 * Created by android on 11/22/16.
 */

public class EditFragment extends DialogFragment implements BottomSheetTimePickerDialog.OnTimeSetListener{

    private TextView fajar;
    private TextView zuhar;
    private TextView asar;
    private TextView magrib;
    private TextView isha;
    private MasjidModel mModel;
    private GridTimePickerDialog grid;
    private java.util.Calendar now;
    private AppCompatActivity mActivity;
    private Context mContext;
    private int tag = 0;
    private Button editButton;
    private String timeFajar;
    private String timezuhar;
    private String timeAsar;
    private String timeMagrib;
    private String timeIsha;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference masjidRef = database.getReference(FirebaseReferences.masjidRef);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity  = (AppCompatActivity) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.content_dialog_edit,container,false);
        getDialog().setTitle("Edit Time");
        initView(view);
        startListener();
        return view;
    }



    private void initView(View view) {
         fajar = (TextView) view.findViewById(R.id.fajar_time);
         zuhar = (TextView) view.findViewById(R.id.zuhar_time);
         asar = (TextView) view.findViewById(R.id.asar_time);
         magrib = (TextView) view.findViewById(R.id.magrib_time);
         isha = (TextView) view.findViewById(R.id.isha_time);
        mContext = getActivity().getApplicationContext();
        now = Calendar.getInstance();
        editButton = (Button) view.findViewById(R.id.edit_btn);
        fillTime();
    }

    private void fillTime() {

        fajar.setText(mModel.getFajarTime());
        zuhar.setText(mModel.getZuharTime());
        asar.setText(mModel.getAsarTime());
        magrib.setText(mModel.getMagribTime());
        isha.setText(mModel.getIshaTime());
    }

    private void startListener() {
        fajar.setOnClickListener(new EditButtons());
        zuhar.setOnClickListener(new EditButtons());
        asar.setOnClickListener(new EditButtons());
        magrib.setOnClickListener(new EditButtons());
        isha.setOnClickListener(new EditButtons());
        editButton.setOnClickListener(new EditButtons());
    }


    public void objectTaker(MasjidModel model)
    {
        mModel = model;
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

        switch (tag) {
            case 1:
                timeFajar = setTime(fajar, hourOfDay, minute);

                break;
            case 2:
                timezuhar = setTime(zuhar, hourOfDay, minute);

                break;
            case 3:
                timeAsar = setTime(asar, hourOfDay, minute);

                break;
            case 4:
                timeMagrib = setTime(magrib, hourOfDay, minute);

            case 5:
                timeIsha = setTime(isha, hourOfDay, minute);

                break;

        }

    }

    private String setTime(TextView textView, int hourOfDay, int minute) {

        now.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(java.util.Calendar.MINUTE, minute);
        String time = DateFormat.getTimeFormat(getActivity()).format(now.getTime());
        textView.setText(": " + time);
        return time;
    }

    private class EditButtons implements View.OnClickListener {
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
                    initDialog("asar");
                    break;
                case R.id.magrib_time:
                    tag =4;
                    initDialog("magrib");
                    break;
                case R.id.isha_time:
                    tag =5;
                    initDialog("isha");
                    break;
                case R.id.edit_btn:
                    editRequest();
                    Toast.makeText(mContext,"Edit Successfull",Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
            }

        }
    }

    private void editRequest() {
        masjidRef.child(mModel.getMasjidID()).child("fajarTime").setValue(fajar.getText());
        masjidRef.child(mModel.getMasjidID()).child("zuharTime").setValue(zuhar.getText());
        masjidRef.child(mModel.getMasjidID()).child("asarTime").setValue(asar.getText());
        masjidRef.child(mModel.getMasjidID()).child("magribTime").setValue(magrib.getText());
        masjidRef.child(mModel.getMasjidID()).child("ishaTime").setValue(isha.getText());
    }


    private void initDialog(String tag) {
        grid = GridTimePickerDialog.newInstance(this,
                now.get(java.util.Calendar.HOUR_OF_DAY),
                now.get(java.util.Calendar.MINUTE),
                DateFormat.is24HourFormat(mContext));
        grid.show(mActivity.getSupportFragmentManager(),tag);
    }

}
