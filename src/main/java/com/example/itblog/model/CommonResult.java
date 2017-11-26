package com.example.itblog.model;

public class CommonResult {
    private String message;
    private Object detail;

    public CommonResult() {
    }

    public CommonResult(String message) {
        this.message = message;
    }

    public CommonResult(String message, Object detail) {
        this.message = message;
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
