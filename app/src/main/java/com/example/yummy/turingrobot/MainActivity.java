package com.example.yummy.turingrobot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity implements DataInterface,View.OnClickListener{

    //图灵API_Key
    private String url = "%E6%9D%AD%E5%B7%9E";

    //存放聊天信息条
    private List<ListData> lists;
    private ListView listView;
    private Adapter adapter;
    private Button sendBt;
    private EditText sendTx;

    //发送内容
    private String sendString;
    //欢迎语
    private String[] welcomeTips;
    //当前时间
    private double currentTime = 0;
    //上次时间
    private double oldtime = 0;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_sendbt:

                //获取时间
                getTime();
                sendString = sendTx.getText().toString();
                sendTx.setText("");

                //取出空格和换行符
                String a = sendString.replace(" ","");
                String b = a.replace("\n","");
                ListData mydata = new ListData(sendString,ListData.SEND,getTime());
                lists.add(mydata);

                //如果列表超过40条信息，就开始移除多余的旧的信息
                if(lists.size()>40){
                    for(int i = 0; i < lists.size(); i++){
                        lists.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            HttpTool tool = (HttpTool)new HttpTool("http://www.tuling123.com/openapi/api?" +
                    "key=15e19f0227727d42a49a945e7263cf81&info="+b,this).execute();
            break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    @Override
    public void getDataUrl(String data) {
        System.out.println(data);
        lists.add(dataParse(data));
        //通知改变
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化View
     */
    public void initView(){

        lists = new ArrayList<ListData>();
        listView = (ListView)this.findViewById(R.id.id_listview);
        sendBt = (Button)this.findViewById(R.id.id_sendbt);
        sendTx = (EditText)this.findViewById(R.id.id_sendtx);
        sendBt.setOnClickListener(this);

        //控制不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //加入问候语句
        ListData listData = new ListData(getWelcomeTips(),ListData.RECEIVER,getTime());
        lists.add(listData);
        //适配内容
        adapter = new Adapter(lists,this);
        listView.setAdapter(adapter);

    }


    /**
     * 从欢迎语中获取
     */
    public String getWelcomeTips(){
        String tip = "";
        welcomeTips = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int)(Math.random()*(welcomeTips.length-1));
        tip = welcomeTips[index];
        return tip;
    }


    /**
     * 获取时间
     *
     */
    public String getTime(){

        //获取当前系统时间
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        Date curDate = new Date();
        String str = format.format(curDate);
        //超过5分钟
        if(currentTime - oldtime >= 5*60*1000){
            oldtime = currentTime;
            return str;
        }else{
            return "";
        }

    }

    /**
     * 解析数据
     * @param result
     * @return
     */
    public  ListData dataParse(String result){

        try{
            if(result.equals("亲，你还没有联网哦!")){
                ListData listData = new ListData(result,ListData.RECEIVER,getTime());
                return listData;
            }else{
                JSONObject jsonObject = new JSONObject(result);
                ListData listData = new ListData(jsonObject.getString("text"),ListData.RECEIVER,getTime());
                return listData;
            }
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("你要离开我了吗？/(ㄒoㄒ)/~").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
