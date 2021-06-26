package com.happy.video.dto;

/**
 * 定义返回数据格式规范
 */
public class ResponseResult<T> {

    // eg
    // 1 成功
    // 0 失败
    // 3 不合法
    private int code;

    // code 字符串描述内容
    // 1 email可以使用
    // 0 email已经被占用
    // 3 用户email不合法
    private String message;

    // 数据
    private T data;


    public ResponseResult() {
    }

    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
