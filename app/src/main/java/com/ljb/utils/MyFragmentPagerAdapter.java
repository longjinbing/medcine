package com.ljb.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.example.longjinbin.medcine.CookFragment;
import com.example.longjinbin.medcine.HomeFragment;
import com.example.longjinbin.medcine.StoreFragment;
import com.ljb.main.MainActivity;
import com.ljb.main.UserFragment;

import java.util.ArrayList;

/**
 * Created by longjinbin on 2018/5/2.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT=4;
    private HomeFragment Home=null;
    private CookFragment Cook=null;
    private StoreFragment Store=null;
    private UserFragment User=null;
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;//fragement管理


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
       this.Home=new HomeFragment();
        this.Cook=new CookFragment();
        this.Store=new StoreFragment();
        this.User=new UserFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case MainActivity.Page_Home:
                fragment=Home;
                break;
            case MainActivity.Page_Cook:
                fragment=Cook;
                break;
            case MainActivity.Page_Store:
                fragment=Store;
                break;
            case MainActivity.Page_User:
                fragment=User;
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }
    /**
     * 缓存清理函数,实现全部刷新,适用于数据影响为比较大的操作,比如登录后实现所有片段的刷新
     * @param fragments 新的fragements列表
     *
     */
    public void setFragments(ArrayList<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    /**
     * 缓存清理函数,实现片段的定向刷新,适用于数据影响的(fragement)片段范围为比较小的操作,比如添加账户后的单个片段的刷新
     * @param position 要更新的fragement在fragments列表的相对位置
     * @param newFragment 新的片段
     */
    public void setFragments(Integer position,Fragment newFragment){
        FragmentTransaction ft = fm.beginTransaction();
        /*
         * 获取第position个的id,适配器是通过这个id来找到相应片段的
         */
        Integer id = this.fragments.get(position).getId();
        /**
         * 替换缓存中的片段，可以用replace实现，当然也可以用如下方法
         */
        ft.remove(this.fragments.get(position));
        ft.add(id, newFragment);
        /**
         * 更新片段列表
         */
        this.fragments.add(position,newFragment);
        /**
         * 别忘了提交
         */
        ft.commit();
        ft = null;
        fm.executePendingTransactions();
        /**
         * 通知适配器数据改变
         */
        notifyDataSetChanged();
    }

}
