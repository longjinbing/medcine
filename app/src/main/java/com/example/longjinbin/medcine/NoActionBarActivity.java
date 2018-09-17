package com.example.longjinbin.medcine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class NoActionBarActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView action_bar_back;
    private TextView tv_title;
    private ImageView iv_right;
    private Intent intent;
    private String resId;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.noactionbarbutton);
        action_bar_back=(ImageView)findViewById(R.id.action_bar_back);
        tv_title=(TextView)findViewById(R.id.tv_title);
        iv_right=(ImageView)findViewById(R.id.iv_right);
        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent = getIntent();

        // 这里需要传递其他值可以自己定义
        //id = intent.getStringExtra("id");
        /**
         * 根据传递过来的不同的资源id值设置不同的fragment
         */
        fManager = getSupportFragmentManager();
        fTransaction = fManager.beginTransaction();
        resId = intent.getStringExtra("resId");
        if (intent.getExtras() != null) {
            Bundle bundle=new Bundle();
            switch (resId){
                case "LoginFragement":
                 fTransaction.replace(R.id.fl_click_button, new Loginfragment());
                 break;
                case "OptionsFragment":
                    fTransaction.replace(R.id.fl_click_button, new Optionsfragment());
                    break;
                default:
                    fTransaction.replace(R.id.fl_click_button, new Loginfragment());
                    break;
            }
        }
        fTransaction.commit();
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            finish();
            return true;
        }
        return false;

    }

}
