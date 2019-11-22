package com.norah1to.simplenotification.Http;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public static final String ApiHost = "http://todo.wegfan.cn/api";

    public static void Login(UserBean userBean) {
        try {
            URL url = new URL(ApiHost + "/users/auth");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            String JsonData = JSON.toJSONString(userBean);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(JsonData.getBytes());
            outputStream.flush();

            final StringBuffer buffer = new StringBuffer();
            int code = connection.getResponseCode();
            if (code == 200) {//成功
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;//一行一行的读取
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);//把一行数据拼接到buffer里
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //把从服务端获取的数据提示出来
                        Log.d("HttpHelper", "run: " + buffer.toString());
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    public static class UserBean {
        public UserBean(String account, String password) {
            this.account = account;
            this.password = password;
        }
        public String account;
        public String password;
    }
}
