package com.appswallet.jamatfinder.Screens;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appswallet.jamatfinder.R;

/**
 * Created by android on 11/17/16.
 */
public class TemporaryJamatFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_masjid, container, false);
            return view;
        }
    }

