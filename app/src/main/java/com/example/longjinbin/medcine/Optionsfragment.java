package com.example.longjinbin.medcine;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by longjinbin on 2018/5/3.
 */

public class Optionsfragment extends Fragment {
    private String Content;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    SharedHelper share;
    @BindView(R.id.groupListView)QMUIGroupListView listView;
    Unbinder unbinder;
    private String ServerIp="http://39.107.107.180:81";

    public Optionsfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.systemoptions, container, false);
        unbinder= ButterKnife.bind(this,view);
        share=new SharedHelper(this.getContext());
        initOptions();
        return view;
    }
    //初始化用户中心
    private void initOptions() {

        QMUICommonListItemView itemWifi=listView.createItemView("仅WIFI下可用");
        itemWifi.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);

        QMUICommonListItemView itemfankui=listView.createItemView("用户反馈");
        itemfankui.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView itemConnect=listView.createItemView("联系我们");
        itemConnect.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        //用户版本
        QMUICommonListItemView itemVersion = listView.createItemView("用户版本");
        itemVersion.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemVersion.setDetailText("标准版");
        //有效期
        QMUICommonListItemView itemExpireDate = listView.createItemView("到期时间");
        itemExpireDate.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemExpireDate.setDetailText("2019年6月21日 10:00:40");
        //有效期
        QMUICommonListItemView itemLogout = listView.createItemView("安全退出");
        itemLogout.setVerticalGravity(QMUIGroupListView.TEXT_ALIGNMENT_CENTER);
        itemLogout.setHorizontalGravity(QMUIGroupListView.TEXT_ALIGNMENT_CENTER);
        itemLogout.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemLogout.getTextView().setTextColor(getResources().getColor(R.color.qmui_config_color_red));


        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemWifi,mOnClickListenerGroup)
                .addItemView(itemfankui,mOnClickListenerGroup)
                .addItemView(itemConnect,mOnClickListenerGroup)
                .addItemView(itemVersion, mOnClickListenerGroup)
                .addItemView(itemExpireDate, mOnClickListenerGroup)
                .addItemView(itemLogout, mOnClickListenerGroup)
                .addTo(listView);
    }
    //统一处理选项点击事件
    private View.OnClickListener mOnClickListenerGroup = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            QMUICommonListItemView viewList = (QMUICommonListItemView) view;
            Intent intent;
            switch (viewList.getText().toString()) {
                case "用户版本":
                    break;
                case "到期时间":
                    break;
                case "退出登录":
                    break;
            }
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
