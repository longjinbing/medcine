package com.example.longjinbin.medcine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ljb.main.WebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class OrderGoodsfragment extends Fragment {
    private String Content;
    private String goodsId;
    private ArrayList<Goods> mData = null;
    private NetworkImageView goodsimg;
    private TextView goodsPrice;
    private TextView goodsTitle;
    private TextView username;
    private TextView provincecity;
    private TextView address;
    private TextView phone;
    private TextView sum;
    private TextView goodsIdbtn;
    private TextView orderGoods;
    private String ServerIp="http://39.107.107.180:81";

    public OrderGoodsfragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.ordergoods, container, false);
        goodsimg = (NetworkImageView) view.findViewById(R.id.goodsimg);
        goodsPrice = (TextView) view.findViewById(R.id.goods_price);
        goodsTitle = (TextView) view.findViewById(R.id.goods_title);
        goodsIdbtn = (TextView) view.findViewById(R.id.goodsId);
        orderGoods = (TextView) view.findViewById(R.id.orderGoods);
        username = (TextView) view.findViewById(R.id.username);
        provincecity = (TextView) view.findViewById(R.id.provincecity);
        address = (TextView) view.findViewById(R.id.address);
        phone = (TextView) view.findViewById(R.id.phone);
        sum = (TextView) view.findViewById(R.id.sum);
        Bundle bundle = getArguments();
        final String goodsId = bundle.getString("goodsId");
        final RequestQueue mQueue = Volley.newRequestQueue(this.getContext());
        final ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ServerIp+"/Api/GoodsDetails/" + goodsId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject goods = null;
                        try {
                            goods = response.getJSONObject("goods");
                            goodsIdbtn.setText(goods.getString("Id"));
                            goodsimg.setImageUrl(ServerIp + goods.getString("ImgSrc"), imageLoader);
                            goodsPrice.setText(goods.getString("Price"));
                            sum.setText(goods.getString("Price"));
                            goodsTitle.setText(goods.getString("Title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
        orderGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerIp+"/Api/ConfirmOrder",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Intent intent = new Intent(getActivity(), WebActivity.class);
                                    if (jsonObject.getString("result") == "true") {
                                        intent.putExtra("resId", "OrderSuccessFragement");
                                    } else {
                                        intent.putExtra("resId", "OrderFailureFragement");
                                        intent.putExtra("goodsId", goodsIdbtn.getText().toString());
                                    }
                                    intent.putExtra("goodsId", goodsIdbtn.getText());
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                       map.put("GoodId", goodsIdbtn.getText().toString());
                        map.put("Name", username.getText().toString());
                        map.put("PayType", "货到付款");
                        map.put("Phone",phone.getText().toString());
                        map.put("Province", provincecity.getText().toString());
                        map.put("City", " ");
                        map.put("Qu", " ");
                        map.put("DetailsAddress", address.getText().toString());
                        return map;
                    }
                };
                mQueue.add(stringRequest);
            }
        });
        return view;

    }
}
