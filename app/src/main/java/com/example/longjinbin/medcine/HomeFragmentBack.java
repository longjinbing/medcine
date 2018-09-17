package com.example.longjinbin.medcine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ljb.entity.Article;
import com.ljb.main.MedcineApplication;
import com.ljb.main.WebActivity;
import com.ljb.utils.Recycler_variety_Adapter;
import com.qmuiteam.qmui.widget.QMUIWrapContentListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jay on 2015/8/28 0028.
 */
@SuppressLint("ValidFragment")
public class HomeFragmentBack extends Fragment {

    private String[] names = new String[]{"女人的美丽，关键在于「肝脾肾」", "小腹不平，何以平天下","气血不通+养肺益气=理气化痰","大S胎停育，生个孩子有那么难吗？备孕深度好文"," 福袋｜夏至姨妈调理福袋预售开启 "};
    private String[] url={"https://api.huofar.com/v61/7.0.8/article/2094?from=singlemessage","https://api.huofar.com/v61/7.0.8/article/2098?from=singlemessage","https://api.huofar.com/v61/7.0.8/article/2090?from=singlemessage","https://api.huofar.com/v61/7.0.8/article/2080?from=singlemessage"};
    private String[] says = new String[]{"", "", ""};
    private int[] imgIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String content;
    private Intent intent;
    private ViewFlipper vflp_help ;
    private RelativeLayout dangan;
    private LinearLayout tuijian1;
    private LinearLayout tuijian2;
    private TextView tizhi;
    private LinearLayout date;
    private TextView day;
    private TextView yearmonth;
    private TextView nongli;
    private TextView nonglinian;
    private RequestQueue mQueue;
    private String ServerIp="http://39.107.107.180:81";
    SharedHelper share;
    @BindView(R.id.article_list)
    RecyclerView recyclerView;
    private LinkedList<Article> articleList;
    Integer count=0;
    Integer page=1;
    private Unbinder unbinder;
    private List<Article> mData=new ArrayList<>();
    private Context mContext;
    private static final String VOLLEY_TAG="ARTICLES";
    Recycler_variety_Adapter adapter;
    private int lastItemPosition;
    private Boolean loadingStatus=true;
    protected WeakReference<View> mRootView;
    @BindView(R.id.scrollView)ScrollView scrollView;
    public HomeFragmentBack() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
        View view = inflater.inflate(R.layout.home,container,false);
        mContext=this.getContext();
        unbinder= ButterKnife.bind(this,view);
        vflp_help = (ViewFlipper) view.findViewById(R.id.home_viewFlipper);
        dangan=(RelativeLayout)view.findViewById(R.id.dangan);
        tuijian1=(LinearLayout)view.findViewById(R.id.tuijian1);
        tuijian2=(LinearLayout)view.findViewById(R.id.tuijian2);
        date=(LinearLayout)view.findViewById(R.id.date);
        share=new SharedHelper(this.getContext());
        mQueue = MedcineApplication.getRequestQueue();
        tizhi=(TextView)view.findViewById(R.id.tizhi);
        day=(TextView)view.findViewById(R.id.day);
        yearmonth=(TextView)view.findViewById(R.id.yearmonth);
        nongli=(TextView)view.findViewById(R.id.nongli);
        nonglinian=(TextView)view.findViewById(R.id.nonglinian);
        setTime();
        Map<String,String> user =share.readuserinfo();
        if(!user.get("tizhi").isEmpty()){
            tizhi.setText(user.get("tizhi"));
        }

        tizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", ServerIp+"/Api/"+tizhi.getText().toString()+".html");
                startActivity(intent);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url","https://m.huofar.com/health/yiji/?tizhi=N&gender=1&from=singlemessage");
                startActivity(intent);
            }
        });
        tuijian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url","https://api.huofar.com/v61/6.1/day/eat/?id=2&day=0&from=singlemessage");
                intent.putExtra("jsclear","true");
                startActivity(intent);
            }
        });
        tuijian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url","https://api.huofar.com/v61/6.1/day/eat/?id=2&day=0&from=singlemessage");
                intent.putExtra("jsclear","true");
                startActivity(intent);
            }
        });
        dangan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent = new Intent(getActivity(), WebActivity.class);
               intent.putExtra("url",ServerIp+"/Api/tresult5.html");
               startActivity(intent);
           }
       });
       intent=new Intent();
        vflp_help.startFlipping();
        vflp_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",ServerIp+"/Api/tintro.html");
                startActivity(intent);
            }
        });
        //要显示的数据
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("item_text", names[i]);
            listitem.add(showitem);
        }
        SimpleAdapter myAdapter = new SimpleAdapter(this.getContext(), listitem, R.layout.list_view_item, new String[]{ "item_text"}, new int[]{ R.id.item_text });
        QMUIWrapContentListView listView = (QMUIWrapContentListView)view.findViewById(R.id.list_view);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",url[position]);
                intent.putExtra("jsclear","true");
                startActivity(intent);
            }
        });
        initData(page);
        initViewOper();
            //纠正首次加载view不置顶问题
            scrollView.smoothScrollTo(0,0);
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

    private void initData(Integer pageNum) {
        if(loadingStatus) {
            loadingStatus=false;
            String url = "http://120.76.205.241:8000/news/qihoo?kw=%E4%BA%94%E8%B0%B7%E5%85%BB%E7%94%9F&pageToken="+pageNum+"&apikey=9frJIRHifJDRnJUxWtOAEW9rxQi73anb7ubCQf24V7i58KN8dJ4JnLV0XHsneZ98";
            Log.e("Home开始访问网络：网址：",url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray array = null;
                    try {
                        array = response.getJSONArray("data");
                        Random random = new Random();
                        for (Integer i = 0; i < array.length(); i++) {
                            Integer type = 0;
                            JSONObject object = array.getJSONObject(i);
                            String imgstr = object.getString("imageUrls");
                            List<String> urls = new ArrayList<>();
                            if (imgstr.length() > 6) {
                                imgstr = imgstr.substring(1, imgstr.length() - 1);
                                if (imgstr.indexOf(",") > -1) {
                                    String[] imgarr = imgstr.split(",");
                                    for (Integer k = 0; k < imgarr.length; k++) {
                                        urls.add(imgarr[k].substring(1, imgarr[k].length() - 1).replace("\\", ""));
                                    }
                                } else {
                                    urls.add(imgstr.substring(1, imgstr.length() - 1).replace("\\", ""));
                                }
                                if (urls.size() > 2) {
                                    type = 3;
                                } else {
                                    type = random.nextInt(3);
                                }
                            } else {
                                continue;
                            }
                            Article article = new Article(object.getString("id"), object.getString("title"), object.getString("url"), urls, type, random.nextInt(1000), object.getString("posterScreenName"));
                            count++;
                            mData.add(article);
                        }
                        adapter.removeFooterView();
                        adapter.updateData(mData);
                        Log.e("llllllll",mData.size()+"");
                        loadingStatus=true;
                        page++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            jsonObjectRequest.setTag(VOLLEY_TAG);//JsonObjectRequestTest_GET
            MedcineApplication.getRequestQueue().add(jsonObjectRequest);
        }
    }

    private void initViewOper() {
        final LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Recycler_variety_Adapter(mData,mContext,MedcineApplication.getRequestQueue());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new Recycler_variety_Adapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                TextView url= view.findViewById(R.id.article_url);
                Intent  intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",url.getText());
                startActivity(intent);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //recyclerView停下来而且可见的item的position是最后一个
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 == adapter.getItemCount()&&loadingStatus) {
                    adapter.addFooterView();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initData(page);
                        }
                    },3000);
                }
            }

            //滚动时获取最后一个可见item的position
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void setTime(){
        Log.e("读取网络时间","开始");
       StringRequest stringRequest = new StringRequest("http://api.huofar.com/v61/7.0/day/yiji?gender=1&tizhi=N",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("aaa",unicodeToChina(response));
                        try{
                            JSONObject jsonArray = new JSONObject(unicodeToChina(response).toString());
                            JSONObject json1=jsonArray.getJSONObject("data");
                            JSONObject json2=json1.getJSONObject("yi");
                            JSONObject json3=json1.getJSONObject("ji");
                            String yititle=json2.getString("title");
                            String yicontent=json2.getString("desc");
                            String jititle=json3.getString("title");
                            String jicontent=json3.getString("desc");
                            String _year=json1.getString("year");
                            String _month=json1.getString("month");
                            String _day=json1.getString("Day");
                            String _week=json1.getString("week");
                            String _n_year=json1.getString("n_year");
                            String _n_month=json1.getString("n_month");
                            String _n_day=json1.getString("n_day");
                            day.setText(_day);
                            yearmonth.setText(_year+"/"+_month);
                            nongli.setText(_n_month+_n_day);
                            nonglinian.setText(_n_year+"年");
                        } catch (JSONException e) {
                           e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int _year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH)+1;
                String  _month=month+"";
                if(month<10){
                   _month="0"+_month;
                }
                int _day = c.get(Calendar.DAY_OF_MONTH);
                Log.e("TAG", _month+"", error);
                day.setText(_day+"");
                yearmonth.setText(_year+"/"+_month);
                DateConversionUtil lunar = new DateConversionUtil(c);
                nongli.setText(lunar.toString());
                nonglinian.setText(lunar.cyclical()+"年");
                Log.e("yyy", lunar.animalsYear()+lunar.cyclical()+lunar.toString(), error);
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
       // https://api.huofar.com/v61/7.0/day/yiji?gender=1&tizhi=N
    }
    public static String unicodeToChina(String str) {
        Charset set = Charset.forName("UTF-16");
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher m = p.matcher( str );
        int start = 0 ;
        int start2 = 0 ;
        StringBuffer sb = new StringBuffer();
        while( m.find( start ) ) {
            start2 = m.start() ;
            if( start2 > start ){
                String seg = str.substring(start, start2) ;
                sb.append( seg );
            }
            String code = m.group( 1 );
            int i = Integer.valueOf( code , 16 );
            byte[] bb = new byte[ 4 ] ;
            bb[ 0 ] = (byte) ((i >> 8) & 0xFF );
            bb[ 1 ] = (byte) ( i & 0xFF ) ;
            ByteBuffer b = ByteBuffer.wrap(bb);
            sb.append( String.valueOf( set.decode(b) ).trim() );
            start = m.end() ;
        }
        start2 = str.length() ;
        if( start2 > start ){
            String seg = str.substring(start, start2) ;
            sb.append( seg );
        }
        return sb.toString() ;
    }
}

