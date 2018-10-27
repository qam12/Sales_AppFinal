package com.example.qamberhaider.sales.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qamberhaider.sales.R;
import com.shinelw.library.ColorArcProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Noteboard extends Fragment {

    private ColorArcProgressBar bar1;
    public Noteboard() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_noteboard, container, false);

        bar1 = (ColorArcProgressBar) v.findViewById(R.id.bar1);

        //graph
        bar1.setCurrentValues(50);


        return v;
    }



}
