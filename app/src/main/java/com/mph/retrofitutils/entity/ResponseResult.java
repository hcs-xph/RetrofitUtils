package com.mph.retrofitutils.entity;

/**
 * 数据请求结果统一预处理实体类（要求服务器返回数据统一格式）
 * 如数据格式为：
 *
 *    {
 *    "status": 0,
 *    "msg": "成功",
 *    "data": {}
 *    }
 *
 * data字段可以是数组字符串等，根据需求而定。
 * 可以具体需求进行更改该类字段格式
 *
 * Created by：hcs on 2016/10/17 15:23
 * e_mail：aaron1539@163.com
 */
public class ResponseResult<T> {
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
