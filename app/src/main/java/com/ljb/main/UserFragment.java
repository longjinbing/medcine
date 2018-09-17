package com.ljb.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.longjinbin.medcine.JsonObjectPostRequest;
import com.example.longjinbin.medcine.NoActionBarActivity;
import com.example.longjinbin.medcine.R;
import com.example.longjinbin.medcine.SharedHelper;
import com.ljb.utils.UrlUtils;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jay on 2015/8/28 0028.
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.username) TextView username;
    @BindView(R.id.user_login_btn) Button user_login_btn;
    @BindView(R.id.userexit) ImageView userexit;
    @BindView(R.id.groupListView) QMUIGroupListView userInfoView;
    @BindView(R.id.cart) TextView cart;
    @BindView(R.id.order) TextView order;
    @BindView(R.id.coupon) TextView coupon;
    @BindView(R.id.collect) TextView collect;
    @BindView(R.id.constitution) QMUICommonListItemView constitution;
    @BindView(R.id.constitution_test) QMUICommonListItemView constitutionTest;
    @BindView(R.id.disease_manage) QMUICommonListItemView diseaseManage;
    @BindView(R.id.family_friends) QMUICommonListItemView familyFriends;
    @BindView(R.id.healthy_manage) QMUICommonListItemView healthyManage;
    @BindView(R.id.user_option) QMUICommonListItemView userOption;
    @BindView(R.id.option) QMUICommonListItemView option;
    private Unbinder unbinder;
    private HashMap<String, String> mMap;
    private FragmentManager fManager;
    private RequestQueue mQueue;
    private SharedHelper share;
    private Map<Integer,String> urlMap;
    protected WeakReference<View> mRootView;
    QMUICommonListItemView itemMyBody;

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
        View view = inflater.inflate(R.layout.user,container,false);
        unbinder=ButterKnife.bind(this,view);
        share=new SharedHelper(this.getContext());
        mQueue = Volley.newRequestQueue(this.getContext());
        if(!MedcineApplication.isLogin){
            Log.e("用户中心:","未登录，准备登录");
            IsLogin();
        }else{
            update();
        }
        initUrlMap();
        BindClick();
        initUserInfo();
            mRootView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
            View view=mRootView.get();
            unbinder= ButterKnife.bind(this,view);
        }
        return mRootView.get();
    }

    //初始化map
    public void initUrlMap(){
        urlMap=new HashMap<>();
        urlMap.put(R.id.cart, UrlUtils.CART_URL);
        urlMap.put(R.id.order, UrlUtils.ORDER_URL);
        urlMap.put(R.id.coupon, UrlUtils.COUPON_URL);
        urlMap.put(R.id.collect, UrlUtils.COLLECT_URL);
        urlMap.put(R.id.constitution, UrlUtils.BODY_TEST_RECORD_URL);
        urlMap.put(R.id.constitution_test, UrlUtils.BODY_TEST_URL);
    }

    //事件绑定
    public  void BindClick(){
        user_login_btn.setOnClickListener(this);
        userexit.setOnClickListener(this);
        cart.setOnClickListener(this);
        order.setOnClickListener(this);
        collect.setOnClickListener(this);
        coupon.setOnClickListener(this);
        constitution.setOnClickListener(this);
        constitutionTest.setOnClickListener(this);
        diseaseManage.setOnClickListener(this);
        healthyManage.setOnClickListener(this);
        familyFriends.setOnClickListener(this);
        userOption.setOnClickListener(this);
        option.setOnClickListener(this);
    }

    //初始化用户中心
    private void initUserInfo() {
        constitution.setText("我的体质");
        constitution.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.tizhi));
        constitution.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        constitutionTest.setText("体质测试");
        constitutionTest.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.ceshi));
        constitutionTest.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        diseaseManage.setText("慢病管理");
        diseaseManage.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.bingli));
        diseaseManage.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        familyFriends.setText("家人和朋友");
        familyFriends.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.pengyou));
        familyFriends.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        healthyManage.setText("健康管理");
        healthyManage.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.jiankang));
        healthyManage.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        userOption.setText("个性设置");
        userOption.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.gexing));
        userOption.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        option.setText("设置");
        option.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.options));
        option.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(urlMap.containsKey(v.getId())){
            intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("url",urlMap.get(v.getId()));
            startActivity(intent);
        }else {
            boolean result=false;
            switch (v.getId()) {
                case R.id.user_login_btn:
                    intent = new Intent(getActivity(), NoActionBarActivity.class);
                    intent.putExtra("resId", "LoginFragement");
                    result=true;
                    startActivityForResult(intent, 1);
                    break;
                case R.id.userexit:
                    result=true;
                    Exit();
                    break;
                default:
                    break;
            }
            if(!result){
                //打开维护页面
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",UrlUtils.NOTFOUND_URL);
                startActivity(intent);
            }
        }
    }
    public boolean IsLogin(){
            final Map<String,String> user =share.readuserinfo();
            String userName = user.get("username").toString().trim();
            String userPassword = user.get("password").toString().trim();
            if(userName!=null&&userPassword!=null){
                Log.e("用户中心:","获取登录信息，准备登录");
                //转成成UTF-8
                try {
                    userName = URLEncoder.encode(userName, "UTF-8");
                    userPassword = URLEncoder.encode(userPassword, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mMap = new HashMap<String, String>();
                mMap.put("username", userName);
                mMap.put("password", userPassword);
                mMap.put("remember", "true");

                //发起请求
                JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(UrlUtils.LOGIN_URL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //从服务器响应response中的jsonObject中取出cookie的值，存到本地sharePreference
                        try {
                            if (jsonObject.getInt("code")==0) {
                                share.saveuserinfo("",mMap.get("username").toString(), mMap.get("password").toString(),"",jsonObject.getString("Cookie"));
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    CookieSyncManager.createInstance(getContext());
                                }
                                CookieManager cookieManager = CookieManager.getInstance();
                                cookieManager.setCookie(UrlUtils.SERVER_IP, jsonObject.getString("Cookie"));//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
                                update();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }, mMap);
                mQueue.add(jsonObjectPostRequest);
            }else{
                Log.e("用户中心:","本地为存储账户信息");
            }
        return true;
    }

    //接收回传数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1) {
            update();
        }
    }

    public void update(){
        Log.e("用户中心：","更新信息");
        Map<String,String> user =share.readuserinfo();
        user_login_btn.setVisibility(View.GONE);
        username.setVisibility(View.VISIBLE);
        username.setText(user.get("username"));
        userexit.setVisibility(View.VISIBLE);
    }
    public void Exit(){
        Log.e("用户中心：","用户退出");
        share.saveuserinfo("","","","","");
        user_login_btn.setVisibility(View.VISIBLE);
        userexit.setVisibility(View.GONE);
        username.setVisibility(View.GONE);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

