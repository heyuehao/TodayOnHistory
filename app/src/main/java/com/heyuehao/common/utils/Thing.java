package com.heyuehao.common.utils;

public class Thing {
    private String date; // 时间
    private String content; // 内容
    private boolean push; // 是否推送

    public Thing() {}

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
