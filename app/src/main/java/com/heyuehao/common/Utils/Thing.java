package com.heyuehao.common.Utils;

import cn.leancloud.AVUser;

public class Thing {
    private AVUser user; // 用户对象
    private String date; // 时间
    private String content; // 内容
    private boolean push; // 是否推送

    public Thing() { }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public AVUser getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }
}
