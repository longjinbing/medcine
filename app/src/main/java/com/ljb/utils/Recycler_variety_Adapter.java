package com.ljb.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.ljb.entity.Article;
import com.example.longjinbin.medcine.R;

import java.util.List;

/**
 * Created by longjinbin on 2018/7/21.
 */

public class Recycler_variety_Adapter extends RecyclerView.Adapter implements View.OnClickListener {

    //定义三种常量  表示三种条目类型
    public static final int TYPE_PULL_IMAGE = 0;
    public static final int TYPE_LEFT_IMAGE = 1;
    public static final int TYPE_RIGHT_IMAGE = 2;
    public static final int TYPE_THREE_IMAGE = 3;
    public static final int TYPE_FOOTER = 4;
    private OnItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private RequestQueue sRequestQueue;
    private AdapterView.OnItemClickListener clickListener;
    private List<Article> mData;
    ImageLoader imageLoader;

    public Recycler_variety_Adapter(List<Article> data,Context context,RequestQueue sRequestQueue) {
        this.mData = data;
        this.mContext=context;
        this.sRequestQueue=sRequestQueue;
        this.imageLoader=new ImageLoader(sRequestQueue, new VolleyBitmapCache());
    }
    //  添加数据
    public void addData(Article article,int position) {
//      在list中添加数据，并通知条目加入一条
        mData.add(position, article);
        //添加动画
        notifyItemInserted(position);
    }

    public void insertRangeDataToHeader(List<Article> list) {
        removeFooterView();
        int lastitem=mData.size();
        mData.addAll(list);
        notifyItemRangeInserted(0,list.size());
    }

    public void insertRangeData(List<Article> list) {
        removeFooterView();
        int lastitem=mData.size();
        mData.addAll(list);
        Log.e("aaaaaaaaaaaa","更新视图");
        notifyItemRangeInserted(lastitem,list.size());
        Log.e("aaaaaaaaaaaa","更新视图成功");
    }

    public void updateData(List<Article> list) {
        int lastitem=mData.size();
        mData.addAll(list);
        notifyItemRangeInserted(lastitem,getItemCount()-1);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //根据viewtype来创建条目
        if (viewType == TYPE_THREE_IMAGE) {
            view =View.inflate(parent.getContext(), R.layout.item_three_img,null);
            view.setOnClickListener(this);
            return new ThreeImageHolder(view);
        } else if (viewType == TYPE_PULL_IMAGE) {
            view =View.inflate(parent.getContext(),R.layout.item_pull_img,null);
            view.setOnClickListener(this);
            return new PullImageHolder(view);
        } else if(viewType == TYPE_LEFT_IMAGE) {
            view =View.inflate(parent.getContext(),R.layout.item_left_img,null);
            view.setOnClickListener(this);
            return new LeftImageHolder(view);
        }else if(viewType == TYPE_FOOTER) {
            view =View.inflate(parent.getContext(),R.layout.item_pull_refersh,null);
            view.setOnClickListener(this);
            return new FooterHolder(view);
        }else{
            view =View.inflate(parent.getContext(),R.layout.item_right_img,null);
            view.setOnClickListener(this);
            return new RightImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LeftImageHolder){
            LeftImageHolder viewHolder = (LeftImageHolder)holder;
            viewHolder.itemView.setTag(position);
            viewHolder.title.setText(mData.get(position).getTitle());
            viewHolder.comment.setText(String.valueOf(mData.get(position).getComment()));
            viewHolder.source.setText(mData.get(position).getSource());
            viewHolder.url.setText(mData.get(position).getUrl());
            viewHolder.id.setText(mData.get(position).getId().toString());
            if(mData.get(position).getImgUrls()!=null) {
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.img, R.mipmap.loadingimg, R.mipmap.loadfailure);
                imageLoader.get(mData.get(position).getImgUrls().get(0), imageListener);
            }

        } else if(holder instanceof RightImageHolder){
            RightImageHolder viewHolder = (RightImageHolder)holder;
            viewHolder.itemView.setTag(position);
            viewHolder.title.setText(mData.get(position).getTitle());
            viewHolder.comment.setText(String.valueOf(mData.get(position).getComment()));
            viewHolder.source.setText(mData.get(position).getSource());
            viewHolder.url.setText(mData.get(position).getUrl());
            viewHolder.id.setText(mData.get(position).getId().toString());
            if(mData.get(position).getImgUrls()!=null) {
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.img, R.mipmap.loadingimg, R.mipmap.loadfailure);
                imageLoader.get(mData.get(position).getImgUrls().get(0), imageListener);
            }

        } else if(holder instanceof PullImageHolder){
            PullImageHolder viewHolder = (PullImageHolder)holder;
            viewHolder.itemView.setTag(position);
            viewHolder.title.setText(mData.get(position).getTitle());
            viewHolder.comment.setText(String.valueOf(mData.get(position).getComment()));
            viewHolder.source.setText(mData.get(position).getSource());
            viewHolder.url.setText(mData.get(position).getUrl());
            viewHolder.id.setText(mData.get(position).getId().toString());
            if(mData.get(position).getImgUrls()!=null) {
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.img, R.mipmap.loadingimg, R.mipmap.loadfailure);
                imageLoader.get(mData.get(position).getImgUrls().get(0), imageListener);
            }
        }else if(holder instanceof ThreeImageHolder){
            ThreeImageHolder viewHolder = (ThreeImageHolder)holder;
            viewHolder.itemView.setTag(position);
            viewHolder.title.setText(mData.get(position).getTitle());
            viewHolder.comment.setText(String.valueOf(mData.get(position).getComment()));
            viewHolder.source.setText(mData.get(position).getSource());
            viewHolder.url.setText(mData.get(position).getUrl());
            viewHolder.id.setText(mData.get(position).getId().toString());
            ImageLoader imageLoader = new ImageLoader(sRequestQueue, new VolleyBitmapCache());
            ImageLoader.ImageListener imageListener1 = ImageLoader.getImageListener(viewHolder.img1, R.mipmap.loadingimg,R.mipmap.loadfailure);
            imageLoader.get(mData.get(position).getImgUrls().get(0), imageListener1);
            ImageLoader.ImageListener imageListener2 = ImageLoader.getImageListener(viewHolder.img2, R.mipmap.loadingimg,R.mipmap.loadfailure);
            imageLoader.get(mData.get(position).getImgUrls().get(1), imageListener2);
            ImageLoader.ImageListener imageListener3 = ImageLoader.getImageListener(viewHolder.img3, R.mipmap.loadingimg,R.mipmap.loadfailure);
            imageLoader.get(mData.get(position).getImgUrls().get(2), imageListener3);
        }else if(holder instanceof FooterHolder  ){
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void addFooterView(){
        Log.e("aaaaaaaaaaaa1",getItemCount()+"");
        int position=getItemCount()!=0?getItemCount()-1:0;
        if(getItemViewType(position)!=4) {
            Article article = new Article("", "", "", null, 4, 1, "");
            addData(article, getItemCount());
            notifyItemInserted(position);
            Log.e("aaaaaaaaaaaa2",getItemCount()+"");
        }
    }
    public void removeFooterView(){
        Log.e("aaaaaaaaaaaa3",getItemCount()+"");
        int position=getItemCount()!=0?getItemCount()-1:0;
        if(position>0) {
            if (getItemViewType(position) == 4) {
                mData.remove(getItemCount() - 1);
                notifyItemRemoved(getItemCount());
                Log.e("aaaaaaaaaaaa4", getItemCount() + "");
            }
        }
    }
    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {

        Article article = mData.get(position);
        if (article.getType() == 0) {
            return TYPE_PULL_IMAGE;
        } else if (article.getType()  == 1) {
            return TYPE_LEFT_IMAGE;
        }else if (article.getType()  == 2) {
            return TYPE_RIGHT_IMAGE;
        } else if(article.getType()==3) {
            return TYPE_THREE_IMAGE;
        }else{
            return TYPE_FOOTER;
        }

    }

    /**
     * 创建三种ViewHolder
     */
    private class PullImageHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView comment;
        TextView source;
        ImageView img;
        TextView url;
        TextView id;

        public PullImageHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.article_title);
            img= itemView.findViewById(R.id.article_image);
            source=itemView.findViewById(R.id.article_source);
            comment=itemView.findViewById(R.id.article_comment);
            url=itemView.findViewById(R.id.article_url);
            id=itemView.findViewById(R.id.article_id);
        }
    }

    private class LeftImageHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView comment;
        TextView source;
        ImageView img;
        TextView url;
        TextView id;
        public LeftImageHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.article_title);
            img= itemView.findViewById(R.id.article_image);
            source=itemView.findViewById(R.id.article_source);
            comment=itemView.findViewById(R.id.article_comment);
            url=itemView.findViewById(R.id.article_url);
            id=itemView.findViewById(R.id.article_id);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView comment;
        TextView source;
        ImageView img;
        TextView url;
        TextView id;
        public RightImageHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.article_title);
            img= itemView.findViewById(R.id.article_image);
            source=itemView.findViewById(R.id.article_source);
            comment=itemView.findViewById(R.id.article_comment);
            url=itemView.findViewById(R.id.article_url);
            id=itemView.findViewById(R.id.article_id);
        }

    }

    private class ThreeImageHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView comment;
        TextView source;
        ImageView img1;
        ImageView img2;
        ImageView img3;
        TextView url;
        TextView id;
        public ThreeImageHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.article_title);
            img1= itemView.findViewById(R.id.article_image1);
            img2= itemView.findViewById(R.id.article_image2);
            img3= itemView.findViewById(R.id.article_image3);
            source=itemView.findViewById(R.id.article_source);
            comment=itemView.findViewById(R.id.article_comment);
            url=itemView.findViewById(R.id.article_url);
            id=itemView.findViewById(R.id.article_id);
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder{
        public FooterHolder(View itemView){
            super(itemView);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}
