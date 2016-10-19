package cn.it.quickindexbar.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.it.quickindexbar.R;
import cn.it.quickindexbar.bean.GoodMan;

/**
 * listView的Adapter
 */
public class MyAdapter extends BaseAdapter {

    private ArrayList<GoodMan> goodMenList;

    public MyAdapter(ArrayList<GoodMan> goodMenList) {
        this.goodMenList = goodMenList;
    }

    @Override
    public int getCount() {
        return goodMenList==null ?0:goodMenList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodMenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(viewGroup.getContext(), R.layout.goodman_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_pinyin = (TextView) convertView.findViewById(R.id.tv_pinyin);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //获取数据
        GoodMan goodMan = goodMenList.get(position);
        String firstPinyin = goodMan.getPinyin().charAt(0)+"";
        holder.tv_pinyin.setText(firstPinyin);
        //如果是第一个条目就让其显示
        if (position == 0){
            holder.tv_pinyin.setVisibility(View.VISIBLE);
            holder.tv_pinyin.setText(firstPinyin);

        }else{//如果是其他条目则和上一个条目比较，如果一样隐藏当前拼音条目，否则反之
            //获取上一个条目展示的英文字母
            String prePinyin = goodMenList.get(position - 1).getPinyin().charAt(0)+"";
            if (TextUtils.equals(prePinyin,firstPinyin)){
                holder.tv_pinyin.setVisibility(View.GONE);
            }else{
                holder.tv_pinyin.setVisibility(View.VISIBLE);
                holder.tv_pinyin.setText(firstPinyin);
            }

        }

        holder.tv_name.setText(goodMan.getName());

        return convertView;
    }

    /** 创建ViewHolder */
    private class ViewHolder{

        TextView tv_pinyin;
        TextView tv_name;

    }

}
