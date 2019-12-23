package com.yangdiansheng.music.view.login.manager;

import com.yangdiansheng.music.view.login.User;

public class UserManager {
    private static UserManager mInstance;
    private User mUser;

    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存用户信息到内存
     */
    public void saveUser(User user) {
        mUser = user;
        saveLocal(user);
    }

    /**
     * 持久化用户信息
     *
     * @param user
     */
    private void saveLocal(User user) {

    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUser() {
        return mUser;
    }

    /**
     * 从本地获取
     *
     * @return
     */
    private User getLocal() {
        return null;
    }

    /**
     * 判断是否登陆过
     *
     * @return
     */
    public boolean hasLogin() {
        return getUser() == null ? false : true;
    }


    public void removeUser() {
        mUser = null;
        removeLocal();
    }

    /**
     * 从库中删掉用户信息
     */
    private void removeLocal() {
    }
}
