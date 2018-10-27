package com.example.qamberhaider.sales.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qamberhaider.sales.Model.ShopDetails;
import com.example.qamberhaider.sales.Model.ShopDetails_Retrive;
import com.example.qamberhaider.sales.R;

import java.util.List;

/**
 * Created by qamber.haider on 8/8/2018.
 */

public class ShopsList extends RecyclerView.Adapter<ShopsList.MyHolder> {


    List<ShopDetails_Retrive> listdata;
    private Context mContext;

//    public static final String KEY_TITLE="TITLE";
//    public static final String KEY_TYPE="TYPE";
//    public static final String KEY_RATE = "RATE";
//    public static final String KEY_REGNO = "RegNo";


    public ShopsList(Context contexts, List<ShopDetails_Retrive> listdata) {
        this.mContext = contexts;
        this.listdata = listdata;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopdetail_row,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        ShopDetails_Retrive data = listdata.get(position);
        holder.shop_Title.setText(data.getTitle_Shop());
        holder.shop_Addres.setText(data.getAddress_Shop());
        holder.shop_Code.setText(data.getCode_Shop());

//        holder.setClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean Onclick) {
//
//                Listdata listdata1 = listdata.get(position);
//
//                Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext,Job_Details.class);
//                intent.putExtra(KEY_TITLE, listdata1.getName());
//                intent.putExtra(KEY_TYPE, listdata1.getEmail());
//                intent.putExtra(KEY_RATE, listdata1.getAddress());
//                //intent.putExtra(KEY_REGNO,dataModel.getRegno());
//                mContext.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

//    public static  class  MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public static  class  MyHolder extends RecyclerView.ViewHolder {
        TextView shop_Title , shop_Addres, shop_Code;
//        private ItemClickListener clickListener;


        public MyHolder(View itemView) {
            super(itemView);
            shop_Title = (TextView) itemView.findViewById(R.id.shop__title);
            shop_Addres = (TextView) itemView.findViewById(R.id.shop__address);
            shop_Code = (TextView) itemView.findViewById(R.id.shop__code);
//            itemView.setOnClickListener(this);


        }
//
//        public void setClickListener(ItemClickListener itemClickListener) {
//            this.clickListener = itemClickListener;
//        }
//
//        @Override
//        public void onClick(View view) {
//            clickListener.onClick(view, getPosition(), false);
//        }
    }

}
