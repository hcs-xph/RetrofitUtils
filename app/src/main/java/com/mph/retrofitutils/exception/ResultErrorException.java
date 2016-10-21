package com.mph.retrofitutils.exception;

/**
 * 自定义异常可以自行修改
 * Created by：hcs on 2016/10/17 16:08
 * e_mail：aaron1539@163.com
 */
public class ResultErrorException extends RuntimeException{

    public ResultErrorException(String msg){
        super(msg);
    }

}
