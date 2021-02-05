package com.example.findyourlove;

import java.util.Date;

public class zMessage {
    public String content;
    public int type;// 0: Send  1: Receive
    public Date time;

    public zMessage(String content, int type) {
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
