package com.example.longjinbin.medcine;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.ljb.main.MedcineApplication;
import com.ljb.utils.UrlUtils;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class Loginfragment extends Fragment {
    private String Content;
    private TextView username;
    private TextView password;
    private TextView btn_register;
    private TextView forgetpassword;
    private TextView resultinfo;
    private TextView btn_login;
    private String isPersistent;
    private AlertDialog alert = null;
    private View view_custom;
    private AlertDialog.Builder builder = null;
    private TextView other;
    SharedHelper share;
    private String ServerIp="http://39.107.107.180:81";

    public Loginfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        username=(TextView)view.findViewById(R.id.username);
        password=(TextView)view.findViewById(R.id.password);
        forgetpassword=(TextView)view.findViewById(R.id.forgetpassword);
        btn_register=(TextView)view.findViewById(R.id.btn_register);
        resultinfo=(TextView)view.findViewById(R.id.resultinfo);
        btn_login=(TextView)view.findViewById(R.id.btn_login);
        share=new SharedHelper(this.getContext());
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

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resultinfo.setText("");
                String userName = username.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                //转成成UTF-8
                try {
                    userName = URLEncoder.encode(userName, "UTF-8");
                    userPassword = URLEncoder.encode(userPassword, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(userName.isEmpty()||userPassword.isEmpty()){
                    resultinfo.setText("请填写登录信息");
                }else {
                    final HashMap<String, String> mMap = new HashMap<String, String>();
                    mMap.put("username", userName);
                    mMap.put("password", userPassword);

                    //发起请求
                    JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(UrlUtils.LOGIN_URL, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            //从服务器响应response中的jsonObject中取出cookie的值，存到本地sharePreference
                            try {
                                Log.e("接收到登陆返回信息:",jsonObject.toString());
                                if (jsonObject.getInt("code") == 0) {
                                    share.saveuserinfo("",mMap.get("username"), mMap.get("password"),"",jsonObject.getString("Cookie"));
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                        CookieSyncManager.createInstance(getContext());
                                    }
                                    CookieManager cookieManager = CookieManager.getInstance();
                                    cookieManager.setCookie(UrlUtils.SERVER_IP, jsonObject.getString("Cookie"));//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
                                    MedcineApplication.isLogin=true;
                                    //登录成功
                                    Intent intent = getActivity().getIntent();
                                    if(!intent.hasExtra("NoLogin")) {
                                                getActivity().setResult(1, intent);
                                    } else {
                                        getActivity().setResult(2, intent);
                                    }
                                    // 1是返回的requestCode
                                    //这里要直接干掉
                                    getActivity().finish();
                                } else {
                                    resultinfo.setText("账号或密码错误");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            resultinfo.setText("请检查网络状况，当前无网络或网络信号差");
                        }
                    }, mMap);
                    mQueue.add(jsonObjectPostRequest);
                }

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                fTransaction.replace(R.id.fl_click_button, new Registerfragment());
                fTransaction.commit();
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
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
                forgetpassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.show();
                    }
                });

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
