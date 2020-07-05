package com.heyuehao.common.LeanCloud;

import cn.leancloud.AVUser;

public class UserLogOut {
    public boolean Logout() {
        AVUser.logOut();
        if(AVUser.getCurrentUser() == null) {
            return true;
        }
        return false;
    }
}
