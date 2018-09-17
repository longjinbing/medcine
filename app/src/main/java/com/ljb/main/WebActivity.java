package com.ljb.main;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.longjinbin.medcine.JsonObjectPostRequest;
import com.example.longjinbin.medcine.NoActionBarActivity;
import com.example.longjinbin.medcine.R;
import com.example.longjinbin.medcine.SharedHelper;
import com.ljb.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView action_bar_back;
    private ImageView action_bar_fenxiang;
    private WebView webView;
    private Intent intent;
    private AlertDialog alert = null;
    private View view_custom;
    private AlertDialog.Builder builder = null;
    private String url;
    private RelativeLayout loading;
    private boolean truepage=true;
    private String returnUrl;
    private RequestQueue mQueue;
    private Map<String,Object> mMap;
    private SharedHelper share;
    private boolean loadError=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weblayout);
        webView=(WebView)findViewById(R.id.webview);
        share=new SharedHelper(this.getApplicationContext());
        mQueue = Volley.newRequestQueue(this.getApplicationContext());
        loading =(RelativeLayout)findViewById(R.id.loading);
        if(!MedcineApplication.isLogin){
            Login();
        }
        intent = getIntent();
        url=intent.getStringExtra("url");
        LoadUrl(url);
    }

    @Override
    public void onClick(View v) {
    }

    public void LoadUrl(String Url){
        if(!isNetworkAvailable(WebActivity.this)){
            Toast.makeText(this, "未连接到网络，无法获取数据，请检查网络连接", Toast.LENGTH_SHORT).show();
        }else {
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
                    // 步骤2：根据协议的参数，判断是否是所需要的url
                    // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                    //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                    Uri uri = Uri.parse(url);
                    // url = 预先约定的 js 协议
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
                            switch (action){
                                case "login":
                                    returnUrl=uri.getQueryParameter("url");
                                    intent = new Intent(WebActivity.this, NoActionBarActivity.class);
                                    intent.putExtra("resId", "LoginFragement");
                                    intent.putExtra("NoLogin", "true");
                                    startActivityForResult(intent, 2);
                                    break;
                                case "goBack":
                                    finish();
                                    break;
                                default:
                                    break;
                            }

                        }
                        return true;
                    } else {
                        webView.loadUrl(url);
                    }
                    return false;
                }

                @Override public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (loadError != true) {
                        loading.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                    }
                }


                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    // 断网或者网络连接超时
                        loadError=false;
                        loading.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                }

                @Override
                public void onReceivedSslError(WebView view,
                                               SslErrorHandler handler, SslError error) {
                    // TODO Auto-generated method stub
                    // handler.cancel();// Android默认的处理方式
                    handler.proceed();// 接受所有网站的证书
                    // handleMessage(Message msg);// 进行其他处理
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
                    if (title.contains("无法找到") || title.contains("错误")) {
                        webView.loadUrl("file:///android_asset/404.html");
                    }
                }
            });
            webView.loadUrl(Url);
        }
    }
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }
    //接收回传数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1) {
            LoadUrl(url);
        }else if(resultCode==2){
            LoadUrl(returnUrl);
        }else{
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Log.e("回退按钮被触发：","WebActivity ");
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);

    }
    public void actionKey(final int keyCode) {
        new Thread () {
            public void run () {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
                                CookieSyncManager.createInstance( getApplicationContext());
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
