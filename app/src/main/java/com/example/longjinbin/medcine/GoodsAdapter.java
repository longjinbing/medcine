package com.example.longjinbin.medcine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by longjinbin on 2018/5/2.
 */

public class GoodsAdapter extends BaseAdapter {

    private List<Goods> goods;
    private Context mContext;

    public  GoodsAdapter(List<Goods> data,Context context){
        this.goods=data;
        this.mContext=context;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fg_goodslist,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.goods_item = (TextView) convertView.findViewById(R.id.list_goods);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.goods_item.setText(goods.get(position).getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    private class ViewHolder{
        TextView goods_item;
    }
}
