package com.aptech.itblog.model;

import java.util.Map;

public class CommonResponseBody {
    private String title;
    private int status;
//    private Map<String, Object> result;
    private Object result;

//    public CommonResponseBody(String title, int status, Map<String, Object> result) {
//        this.title = title;
//        this.status = status;
//        this.result = result;
//    }

    public CommonResponseBody(String title, int status, Object result) {
        this.title = title;
        this.status = status;
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
