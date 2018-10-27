package com.example.qamberhaider.sales.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qamberhaider.sales.Activity.OrderList;
import com.example.qamberhaider.sales.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order extends Fragment {

    TextView Allorder;

    public Order() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        Allorder = (TextView) v.findViewById(R.id.listorder);
        Allorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderList.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
