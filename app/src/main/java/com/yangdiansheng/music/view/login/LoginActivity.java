package com.yangdiansheng.music.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.yangdiansheng.lib_common_ui.base.BaseActivity;
import com.yangdiansheng.lib_network.response.listener.DisposeDataListener;
import com.yangdiansheng.lib_network.utils.ResponseEntityToModule;
import com.yangdiansheng.music.R;
import com.yangdiansheng.music.api.MockData;
import com.yangdiansheng.music.view.login.manager.UserManager;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements DisposeDataListener{

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
        setContentView(R.layout.activity_login_layout);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.get().post("45556777");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Produce
    public String produceFood(){
        return "this is breads";
    }

    @Produce(
            thread = EventThread.IO,
            tags = {
                    @Tag("11111")
            }
    )
    public ArrayList<String> produceMoreFood() {
        List<String> list = new ArrayList<>();
        list.add("4444");
        list.add("2222");
        list.add("3333");
        return (ArrayList<String>) list;
    }

    @Override
    public void onSuccess(Object responseObj) {
        User user = (User) responseObj;
        UserManager.getInstance().saveUser(user);
        finish();
    }

    @Override
    public void onFailure(Object reasonObj) {
        onSuccess(ResponseEntityToModule.parseJsonToModule(MockData.LOGIN_DATA,User.class));
    }
}
