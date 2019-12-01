package com.norah1to.simplenotification.Http;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.norah1to.simplenotification.BaseActivity;
import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Entity.User;
import com.norah1to.simplenotification.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    public static final String TAG = "HttpHelper";

    private static final String MSG_NET_ERR = "网络请求出现问题";

    private static final String MSG_LOGIN_SUCCESS = "登入成功";
    private static final String MSG_LOGIN_FAIL = "登入失败";

    private static final String MSG_REGISTER_SUCCESS = "注册成功";
    private static final String MSG_REGISTER_FAIL = "注册失败";

    private static final String MSG_LOGOUT_SUCCESS = "登出成功";
    private static final String MSG_LOGOUT_FAIL = "登出失败";

    private static final String MSG_SYNC_SUCCESS = "同步完成";
    private static final String MSG_SYNC_FAIL = "同步失败";
    private static final String MSG_SYNC_UNLOGIN = "未登入";

    private static final String MSG_CHANGE_PASSWORD_SUCCESS = "更改密码成功";
    private static final String MSG_CHANGE_PASSWORD_FAIL = "更改密码失败";

    private static final String ApiHost = "http://todo.wegfan.cn/api/";

    private static final String ROUTE_LOGIN = "users/auth/";
    private static final String ROUTE_REGISTER = "users/";
    private static final String ROUTE_LOGOUT = "users/logout/";
    private static final String ROUTE_SYNC_TODO = "todos/";
    private static final String ROUTE_SYNC_TAG = "tags/";
    private static final String ROUTE_CHANGE_PASSWORD = "users/change/";


    // 更改密码
    public static ResultBean changePssword(
            Handler mainHandel,
            ChangePasswordBean changePasswordBean) {
        Response response = baseRequest(
                ROUTE_CHANGE_PASSWORD, JSON.toJSONString(changePasswordBean));
        try {
            if (response != null) {
                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                if (jsonObject.getBoolean("success")) {
                    return new ResultBean(true, MSG_CHANGE_PASSWORD_SUCCESS);
                } else {
                    return new ResultBean(false, MSG_CHANGE_PASSWORD_FAIL);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "changePssword: ", e);
        }
        return new ResultBean(false, MSG_NET_ERR);
    }


    // 同步所有内容
    public static ResultBean syncData(
            Handler mainHandel,
            List<Todo> createTodoList, List<Todo> updateTodoList, List<Todo> deleteTodoList,
            List<Tag> createTagList, List<Tag> updateTagList, List<Tag> deleteTagList) {
        // TODO: SYNC

        // 判断是否登入
        User user = BaseActivity.userViewModel.getmUser().getValue();
        if (user == null) {
            return new ResultBean(false, MSG_SYNC_UNLOGIN);
        }

        Log.d(TAG, "syncData: User: " + user.getSessionID());

        /**
         *  同步Todo
         */
        // 封装要传输的数据
        JSONObject jsonObject = new JSONObject();
        Log.e(TAG, "syncData: last_sync_time" + user.getLastSyncTimestamp(), null);
        jsonObject.put("last_sync_timestamp", user.getLastSyncTimestamp());
        jsonObject.put("added", createTodoList);
        jsonObject.put("updated", updateTodoList);
        jsonObject.put("deleted", deleteTodoList);
        Log.d(TAG, "syncData: jsonObj: \n" + jsonObject);
        Response response = baseRequest(ROUTE_SYNC_TODO, jsonObject.toJSONString());
        try {
            if (response != null) {
                String jsonStr = response.body().string();
                Log.d(TAG, "syncData: " + jsonStr);
                JSONObject resultSyncTodo = JSONObject.parseObject(jsonStr);
                if (resultSyncTodo.getBoolean("success")) {
                    List<Todo> insertTodoList = new ArrayList<Todo>();
                    JSONObject resultDataJson = JSON.parseObject(
                            resultSyncTodo.getString("data"));
                    insertTodoList.addAll(
                            JSON.parseArray(resultDataJson.getString("added"), Todo.class));
                    insertTodoList.addAll(
                            JSON.parseArray(resultDataJson.getString("updated"), Todo.class));
                    insertTodoList.addAll(
                            JSON.parseArray(resultDataJson.getString("deleted"), Todo.class));
                    Log.d(TAG, "syncData: todolistSIZE " + insertTodoList.size());
                    MainActivity.mtodoViewModel.insertTodo(
                            insertTodoList.toArray(new Todo[0]));
                } else {
                    return new ResultBean(false, MSG_SYNC_FAIL);
                }
            } else {
                return new ResultBean(false, MSG_SYNC_FAIL);
            }
        } catch (Exception e) {
            Log.e(TAG, "syncDataTodo: ", e);
        }


        /**
         * 同步Tag
         */
        // 封装要传输的数据
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("last_sync_timestamp", user.getLastSyncTimestamp());
        jsonObject1.put("added", createTagList);
        jsonObject1.put("updated", updateTagList);
        jsonObject1.put("deleted", deleteTagList);
        Log.d(TAG, "syncData: jsonObj1: \n" + jsonObject1);
        Response response1 = baseRequest(ROUTE_SYNC_TAG, jsonObject1.toJSONString());
        try {
            if (response1 != null) {
                String jsonStr =response1.body().string();
                Log.d(TAG, "syncData: " + jsonStr);
                JSONObject resultSyncTag = JSONObject.parseObject(jsonStr);
                if (resultSyncTag.getBoolean("success")) {
                    List<Tag> insertTagList = new ArrayList<Tag>();
                    JSONObject resultDataJson = JSON.parseObject(
                            resultSyncTag.getString("data"));
                    insertTagList.addAll(
                            JSON.parseArray(resultDataJson.getString("added"), Tag.class));
                    insertTagList.addAll(
                            JSON.parseArray(resultDataJson.getString("updated"), Tag.class));
                    insertTagList.addAll(
                            JSON.parseArray(resultDataJson.getString("deleted"), Tag.class));
                    MainActivity.mtodoViewModel.insertTag(
                            insertTagList.toArray(new Tag[0]));
                    return new ResultBean(true, MSG_SYNC_SUCCESS);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "syncDataTag: ", e);
        }

        return new ResultBean(false, MSG_NET_ERR);
    }


    // logout 请求
    public static ResultBean Logout(Handler mainHandle) {
        Response response = baseRequest(ROUTE_LOGOUT, "{}");
        try {
            if (response != null) {
                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                // 成功删除本地用户信息
                if (jsonObject.getBoolean("success")) {
                    BaseActivity.userViewModel.deleteUser();
                    return new ResultBean(true, MSG_LOGOUT_SUCCESS);
                } else {
                    return new ResultBean(false, MSG_LOGOUT_FAIL);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Register: ", e);
        }
        return new ResultBean(false, MSG_NET_ERR);
    }

    // register 请求
    public static ResultBean Register(UserBean userBean, Handler mainHandle) {
        Response response = baseRequest(ROUTE_REGISTER, JSONObject.toJSONString(userBean));
        try {
            if (response != null) {
                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                if (jsonObject.getBoolean("success")){
                    return new ResultBean(true, MSG_REGISTER_SUCCESS);
                } else {
                    return new ResultBean(false, MSG_REGISTER_FAIL);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Register: ", e);
        }
        return new ResultBean(false, MSG_NET_ERR);
    }

    // login 请求
    public static ResultBean Login(UserBean userBean, Handler mainHandle) {
        Response response = baseRequest(ROUTE_LOGIN, JSON.toJSONString(userBean));
        try {
            if (response != null) {
                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                // 获得当前的 user 状态，没有则新建
                User tmpUser = BaseActivity.userViewModel.getmUser().getValue();
                if (tmpUser == null) {
                    tmpUser = new User();
                    Log.e(TAG, "Login: new User", null);
                }
                tmpUser.setLastSyncTimestamp(new Date(0));
                // 若成功，更新/插入 user
                if (jsonObject.getBoolean("success")){
                    tmpUser.setUserID(JSONObject.parseObject(jsonObject.getString("data")).getString("uuid"));
                    tmpUser.setAccount(userBean.account);
                    tmpUser.setPassword(userBean.password);
                    String sessionID = response.header("Set-Cookie").split("session=")[1].split(";")[0];
                    Log.d(TAG, "Login: sessionID: " + sessionID);
                    tmpUser.setSessionID(sessionID);
                    User finalTmpUser = tmpUser;
                    mainHandle.post(() -> {
                        BaseActivity.userViewModel.insert(finalTmpUser);
                    });
                    return new ResultBean(true, MSG_LOGIN_SUCCESS);
                } else {
                    return new ResultBean(false, MSG_LOGIN_FAIL);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Login: ", e);
            return new ResultBean(false, MSG_NET_ERR);
        }
        return new ResultBean(false, MSG_NET_ERR);
    }


    // 基础请求
    private static Response baseRequest(String route , @NonNull String jsonStr) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .callTimeout(10_000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10_000, TimeUnit.MILLISECONDS)
                    .readTimeout(10_000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10_000, TimeUnit.MILLISECONDS)
                    .build();
            MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(jsonType, jsonStr);
            User tmpUser = BaseActivity.userViewModel.getmUser().getValue();
            String sessionID = null;
            if (tmpUser != null) {
                sessionID = tmpUser.getSessionID();
            }
            String cookieContent = "";
            if (sessionID != null) {
                cookieContent = new StringBuilder()
                        .append("session=")
                        .append(sessionID)
                        .append(";")
                        .toString();
            }
            Request request = new Request.Builder()
                    .header("Cookie", cookieContent)
                    .url(ApiHost + route)
                    .post(body)
                    .build();
            //创建Call对象
            Call call = client.newCall(request);
            //通过execute()方法获得请求响应的Response对象
            Response response = call.execute();
            if (response.isSuccessful() || (response.code() >=300 && response.code() < 400)) {
                return response;
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Login: ", e);
            return null;
        }
    }

    // 接收结果 bean
    public static class ResultBean {
        public ResultBean(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // 用户登录/注册 Bean
    public static class UserBean {
        public UserBean(String account, String password) {
            this.account = account;
            this.password = password;
        }

        private String account;
        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // 用户登录/注册 Bean
    public static class ChangePasswordBean {
        public ChangePasswordBean(String oldPassword, String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        private String oldPassword;
        private String newPassword;

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
