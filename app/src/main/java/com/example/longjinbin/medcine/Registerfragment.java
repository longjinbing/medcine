package com.example.longjinbin.medcine;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ljb.utils.UrlUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class Registerfragment extends Fragment {
    private String Content;
    private TextView username;
    private TextView name;
    private TextView password;
    private TextView phone;
    private RadioGroup xingbie;
    private TextView confirmpassword;
    private TextView resultinfo;
    private TextView btn_login;
    private Button btn_register;
    private String isPersistent;
    private AlertDialog alert = null;
    private View view_custom;
    private AlertDialog.Builder builder = null;
    private String ServerIp="http://39.107.107.180:81";
    TextView other;

    public Registerfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);
        username=(TextView)view.findViewById(R.id.username);
        name=(TextView)view.findViewById(R.id.name);
        password=(TextView)view.findViewById(R.id.password);
        confirmpassword=(TextView)view.findViewById(R.id.confirmpassword);
        xingbie=(RadioGroup)view.findViewById(R.id.xingbie);
        phone=(TextView)view.findViewById(R.id.phone);
        resultinfo=(TextView)view.findViewById(R.id.resultinfo);
        btn_login=(TextView)view.findViewById(R.id.btn_login);
        btn_register=(Button)view.findViewById(R.id.btn_register);
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
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resultinfo.setText("");
               String UserName=username.getText().toString();
               String Name=name.getText().toString();
                String PassWord=password.getText().toString();
                String ConfirmPassWord=confirmpassword.getText().toString();
                String Phone= phone.getText().toString();
                if(UserName.isEmpty()||Name.isEmpty()||PassWord.isEmpty()||ConfirmPassWord.isEmpty()||Phone.isEmpty()){
                    resultinfo.setText("请填写完整信息");
                }else {
                    if(!PassWord.equals(ConfirmPassWord)){
                        resultinfo.setText("两次密码输入不一致");
                    }else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.REGISTER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.e("注册返回信息：", response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            resultinfo.setText(jsonObject.getString("msg"));
                                            Log.e("TAG", response);
                                            if (jsonObject.getInt("code") == 0) {
                                                FragmentManager fManager = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fTransaction = fManager.beginTransaction();
                                                fTransaction.replace(R.id.fl_click_button, new Loginfragment());
                                                fTransaction.commit();
                                            } else {
                                                resultinfo.setText("注册失败，请重试");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                resultinfo.setText("请检查网络状况，当前无网络或网络信号差");
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                for (int i = 0; i < xingbie.getChildCount(); i++) {
                                    RadioButton rd = (RadioButton) xingbie.getChildAt(i);
                                    if (rd.isChecked()) {
                                        map.put("XingBie", rd.getText().toString());
                                        break;
                                    }
                                }
                                map.put("username", username.getText().toString());
                                map.put("name", name.getText().toString());
                                map.put("email", "666666@qq.com");
                                map.put("password", password.getText().toString());
                                map.put("confirmpassword", confirmpassword.getText().toString());
                                map.put("phone", phone.getText().toString());
                                return map;
                            }
                        };
                        mQueue.add(stringRequest);
                    }
                }

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               FragmentManager fManager =getActivity().getSupportFragmentManager();;
                FragmentTransaction fTransaction = fManager.beginTransaction();
                 fTransaction.replace(R.id.fl_click_button, new Loginfragment());
                fTransaction.commit();
            }
        });
        other=(TextView)view.findViewById(R.id.otherquestion);
        other.findViewById(R.id.otherquestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Builder
                builder = new AlertDialog.Builder(getActivity());
                //加载自定义的那个View,同时设置下
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                view_custom = inflater.inflate(R.layout.forgetpassword_dialog, null,false);
                builder.setView(view_custom);
                builder.setCancelable(false);
                alert = builder.create();
                alert.show();

                view_custom.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.show();
                    }
                });
            }
        });
        return view;

    }
}
