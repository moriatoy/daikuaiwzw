package com.fuzhuangfaqianla.xinhuahua.ui.main.bean;

import java.io.Serializable;

/**
 * Created by dasiy on 17/5/8.
 */

public class Message implements Serializable{
    int noticeId;
    String title;
    String intro;
    String content;
    String createBy;
    long sendTime;
    boolean send;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    @Override
    public String toString() {
        return "Message{" +
                "noticeId=" + noticeId +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", send=" + send +
                '}';
    }
}
