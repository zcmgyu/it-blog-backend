package com.example.itblog.model;

public class CommonResponseBody {
    private String title;
    private int status;
    private Object echo;
    private CommonResult result;

    public CommonResponseBody() {
    }

    public CommonResponseBody(String title, int status, Object echo, CommonResult result) {
        this.title = title;
        this.status = status;
        this.echo = echo;
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getEcho() {
        return echo;
    }

    public void setEcho(Object echo) {
        this.echo = echo;
    }

    public CommonResult getResult() {
        return result;
    }

    public void setResult(CommonResult result) {
        this.result = result;
    }
}
