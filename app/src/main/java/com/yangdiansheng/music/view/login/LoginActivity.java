package com.yangdiansheng.music.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yangdiansheng.lib_common_ui.base.BaseActivity;
import com.yangdiansheng.lib_network.response.listener.DisposeDataListener;
import com.yangdiansheng.lib_network.utils.ResponseEntityToModule;
import com.yangdiansheng.music.R;
import com.yangdiansheng.music.api.MockData;
import com.yangdiansheng.music.api.RequestCenter;
import com.yangdiansheng.music.view.login.manager.UserManager;
import com.yangdiansheng.music.view.login.user.LoginEvent;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity implements DisposeDataListener{

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCenter.login(LoginActivity.this);
            }
        });
    }

    @Override
    public void onSuccess(Object responseObj) {
        User user = (User) responseObj;
        UserManager.getInstance().saveUser(user);
        EventBus.getDefault().post(new LoginEvent());
        finish();
    }

    @Override
    public void onFailure(Object reasonObj) {
        onSuccess(ResponseEntityToModule.parseJsonToModule(MockData.LOGIN_DATA,User.class));
    }
}
