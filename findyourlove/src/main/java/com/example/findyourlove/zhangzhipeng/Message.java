package com.example.findyourlove.zhangzhipeng;

import java.util.Date;

public class Message {
    public String content;
    public int type;// 0: Send  1: Receive
    public Date time;

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
