package com.ljb.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ljb.service.BackHandledInterface;
import com.ljb.serviceImpl.BackHandledFragment;
import com.ljb.utils.MyFragmentPagerAdapter;
import com.example.longjinbin.medcine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BackHandledInterface, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.nav_bar)
    RadioGroup nav_bar;
    @BindView(R.id.nav_home)
    RadioButton nav_home;
    @BindView(R.id.nav_store)
    RadioButton nav_store;
    @BindView(R.id.nav_cook)
    RadioButton nav_cook;
    @BindView(R.id.nav_user)
    RadioButton nav_user;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MyFragmentPagerAdapter mAdapter;
    public static final int Page_Home = 0;
    public static final int Page_Cook = 1;
    public static final int Page_Store = 2;
    public static final int Page_User = 3;
    private static MainActivity mInstance;
    private BackHandledFragment mBackHandedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        BindViews();
        nav_home.setChecked(true);
    }

    public static MainActivity getInstance() {
        if (mInstance == null) {
            mInstance = new MainActivity();
        }
        return mInstance;
    }

    public void BindViews() {
        nav_bar.setOnCheckedChangeListener(this);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkId) {
        switch (checkId) {
            case R.id.nav_home:
                viewPager.setCurrentItem(Page_Home);
                break;
            case R.id.nav_cook:
                viewPager.setCurrentItem(Page_Cook);
                break;
            case R.id.nav_store:
                viewPager.setCurrentItem(Page_Store);
                break;
            case R.id.nav_user:
                viewPager.setCurrentItem(Page_User);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case Page_Home:
                    nav_home.setChecked(true);
                    break;
                case Page_Cook:
                    nav_cook.setChecked(true);
                    break;
                case Page_Store:
                    nav_store.setChecked(true);
                    break;
                case Page_User:
                    nav_user.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

}
