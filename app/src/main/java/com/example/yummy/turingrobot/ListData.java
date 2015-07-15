package com.example.yummy.turingrobot;

import android.text.StaticLayout;

/**
 * 数据封装类
 * Created by yummy on 2015/7/12.
 */
public class ListData {

    private String content;
    private  int flag;
    private String time;

    //发送标志
    public static final int SEND = 0;
    //请求标志
    public static final int RECEIVER = 1;

    public ListData(String content,int flag,String time) {
        this.content = content;
        this.flag = flag;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
