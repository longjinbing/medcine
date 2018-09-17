package com.example.longjinbin.medcine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ljb.main.MedcineApplication;
import com.ljb.main.WebActivity;
import com.ljb.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jay on 2015/8/28 0028.
 */
@SuppressLint("ValidFragment")
public class StoreFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.loading_text)
    TextView loadingText;
    private Context mContext;
    private Unbinder unbinder;
    private RequestQueue mQueue;
    private Map<String,Object> mMap;
    private SharedHelper share;
    protected WeakReference<View> mRootView;
    private boolean loadError=true;
    public StoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.store,container,false);
            mContext = this.getContext();
            unbinder= ButterKnife.bind(this,view);
            share=new SharedHelper(getContext());
            mQueue = Volley.newRequestQueue(getContext());
            if(!MedcineApplication.isLogin){
                Login();
            }
            LoadUrl(UrlUtils.GOODS_URL);
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
    public void LoadUrl(String Url){
            loading.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            settings.setAllowFileAccess(true);
            settings.setAppCacheEnabled(true);
            settings.setSaveFormData(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setLoadWithOverviewMode(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                settings.setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            if (Build.VERSION.SDK_INT >= 21) {
                settings.setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    Uri uri = Uri.parse(url);
                    if (uri.getScheme().equals("js")) {
                        loading.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                        // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                        // 所以拦截url,下面JS开始调用Android需要的方法
                        if (uri.getAuthority().equals("webview")) {
                            // 可以在协议上带有参数并传递到Android上
                            Log.e("接收JS调用java方法URL：", uri.toString());
                            Log.e("JS调用参数", uri.getQueryParameter("action"));
                            String action=uri.getQueryParameter("action");
                            Intent intent;
                            switch(action){
                                case "login":
                                    intent = new Intent(getActivity(), NoActionBarActivity.class);
                                    intent.putExtra("resId", "LoginFragement");
                                    intent.putExtra("NoLogin", "true");
                                    startActivityForResult(intent, 2);
                                    break;
                            }

                        }
                        return true;
                    } else {
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                        return true;
                    }
                }
                @Override public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (loadError != true) {
                        loadingText.setText("请检查网络连接，网络连接错误");
                        loading.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                    }
                }


                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    // 断网或者网络连接超时
                    loadError=false;
                    loadingText.setText("请检查网络连接，网络连接错误");
                    loading.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }

                @Override
                public void onReceivedSslError(WebView view,
                                               SslErrorHandler handler, SslError error) {
                    // TODO Auto-generated method stub
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    Log.e("TAG", "onProgressChanged:----------->" + newProgress);
                    if(newProgress==100){
                        loading.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    Log.e("TAG", "onReceivedTitle:title ------>" + title);
                    if (title.contains("404") ||title.contains("无法找到") || title.contains("错误")) {
                        loading.setVisibility(View.VISIBLE);
                        loadingText.setText("服务器遇到了一点小问题，正在努力修复中");
                        webView.setVisibility(View.GONE);
                   Timer timer = new Timer();
                        Long time=10*1000L;
                        timer.schedule(new refershUrl(),time);
                    }
                }
            });
            webView.loadUrl(Url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shopcar:
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",UrlUtils.CART_URL);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public class refershUrl extends TimerTask {

        public void run() {
            webView.loadUrl(UrlUtils.GOODS_URL);
        }
    }
    //接收回传数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2) {
            LoadUrl(UrlUtils.GOODS_URL);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
    public boolean Login(){
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
            mMap = new HashMap<String, Object>();
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
                            MedcineApplication.isLogin=true;
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
}

