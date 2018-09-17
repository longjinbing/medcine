package com.example.longjinbin.medcine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljb.main.WebActivity;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class OrderFailurefragment extends Fragment {
    private String Content;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    public OrderFailurefragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.orderfailure, container, false);
        TextView btn=(TextView)view.findViewById(R.id.btn_continue);
      final String goodsId= getActivity().getIntent().getStringExtra("goodsId");
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), WebActivity.class);
               intent.putExtra("resId", "OrderGoodsFragement");
               intent.putExtra("goodsId", goodsId);
               startActivity(intent);
           }
       });
            return view;
}
}
