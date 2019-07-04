package com.redhood.xtz.bean;

public class LearningGroupBean {
    private String profile_pic;
    private String name;
    private String msg;
    private String pic_url;
    private String date;

    public LearningGroupBean(String profile_pic, String name, String msg, String pic_url, String date) {
        this.profile_pic = profile_pic;
        this.name = name;
        this.msg = msg;
        this.pic_url = pic_url;
        this.date = date;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
