package com.aptech.itblog.model;

import java.util.Map;

public class CommonResponseBody {
    private String title;
    private int status;
    private Map<String, Object> result;

    public CommonResponseBody(String title, int status, Map<String, Object> result) {
        this.title = title;
        this.status = status;
        this.result = result;
    }

    public CommonResponseBody(String badRequest, int value, String echo, CommonResult commonResult) {

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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
