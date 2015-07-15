package com.example.yummy.turingrobot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yummylau.turingrobot.ui.CircularImage;

import java.util.List;

/**
 * 适配器
 * Created by yummy on 2015/7/12.
 */
public class Adapter extends BaseAdapter{

    private List<ListData> lists;
    private Context context;
    private LayoutInflater inflater;
    private ViewHolderLeft left;
    private ViewHolderRight right;

    //布局数目
    private final int VIEW_TYPE = 2;
    //0为发送
    private final int TYPE_0 = 0;
    //1为接受
    private final int TYPE_1 = 1;



    public Adapter(List<ListData> lists,Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 说明下这个方法。每一个view被调用的时候都会调用此方法，获取当前所需要的view样式
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return (lists.get(position).getFlag()==ListData.SEND)?TYPE_0:TYPE_1;
    }


    /**
     * 返回布局类型
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        left = null;
        right = null;

        inflater = LayoutInflater.from(context);

        int tpye = getItemViewType(position);
       //System.out.print(tpye+"                             ddd            ");

        //初始化布局
        if(convertView == null){

            switch (tpye){

                //0为发送
                case TYPE_0:
                    convertView = inflater.inflate(R.layout.rightitem,null);
                    right = new ViewHolderRight();
                    right.rtextView = (TextView)convertView.findViewById(R.id.id_tv);
                    right.rtime = (TextView)convertView.findViewById(R.id.id_time);
                    right.riv = (CircularImage)convertView.findViewById(R.id.id_iv);
                    convertView.setTag(right);
                    break;

                //1为接受
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.leftitem,null);
                    left = new ViewHolderLeft();
                    left.ltextView = (TextView)convertView.findViewById(R.id.id_tv);
                    left.ltime = (TextView)convertView.findViewById(R.id.id_time);
                    left.liv = (CircularImage)convertView.findViewById(R.id.id_iv);
                    convertView.setTag(left);
                    break;
            }

        }else{

            switch (tpye){

                //0为发送
                case TYPE_0:
                    right = (ViewHolderRight)convertView.getTag();
                    break;

                //1为接收
                case TYPE_1:
                    left = (ViewHolderLeft)convertView.getTag();
                    break;

            }

        }


        /**
         * 设置每个布局的资源
         */
        switch (tpye){

            //0为发送
            case TYPE_0:
                right.rtextView.setText(lists.get(position).getContent());
                right.rtime.setText(lists.get(position).getTime());
                right.riv.setImageResource(R.drawable.youtouxiang);
                break;

                //1为接收
            case TYPE_1:
                left.ltextView.setText(lists.get(position).getContent());
                left.ltime.setText(lists.get(position).getTime());
                left.liv.setImageResource(R.drawable.zuotouxiang);
                break;

        }


        return convertView;

    }


    //左侧缓冲类
    static class ViewHolderLeft{
        TextView ltextView;
        TextView ltime;
        CircularImage liv;
    }

    //右侧缓冲类
    static class ViewHolderRight{
        TextView rtextView;
        TextView rtime;
        CircularImage riv;
    }


}
