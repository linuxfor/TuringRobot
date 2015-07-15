package com.example.yummy.turingrobot;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 异步网络访问类
 * Created by yummy on 2015/7/12.
 */
public class HttpTool extends AsyncTask<String,Void,String>{


    private String url;
    private HttpClient httpClient;
    private HttpGet httpGet;
    private HttpResponse httpResponse;
    private HttpEntity httpEntity;
    private InputStream inputStream;

    //使用回调的方法
    private DataInterface data;



    public HttpTool(String url,DataInterface data) {
        this.url = url;
        this.data = data;
    }

    /**
     * 实现后台网络访问获取数据
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {

        try{
            httpClient = new DefaultHttpClient();
            httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            httpResponse.getStatusLine();
            inputStream = httpEntity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }

            return stringBuffer.toString();
        }catch(Exception e){
            return new String("亲，你还没有联网哦!");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        data.getDataUrl(s);
        super.onPostExecute(s);
    }
}
