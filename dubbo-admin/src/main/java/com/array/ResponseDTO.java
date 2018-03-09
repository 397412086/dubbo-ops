package com.array;


/**
 * 请求返回结果值
 * Created by cdf on 2017/9/21.
 */
public class ResponseDTO<T> {


    /**
     * 返回编码
     */
    private int    code;
    /**
     * 返回编码
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;





    public ResponseDTO() {
    }

    public ResponseDTO(MsgCodeEnum msgCodeEnum) {
        this.code = msgCodeEnum.getCode();
        this.message = msgCodeEnum.getMessage();
    }

    public ResponseDTO(MsgCodeEnum msgCodeEnum, T data) {
        this.code = msgCodeEnum.getCode();
        this.message = msgCodeEnum.getMessage();
        this.data = data;
    }

    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDTO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public ResponseDTO setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseDTO<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseDTO<T> setData(T data) {
        this.data = data;
        return this;
    }

    //-------------------------静态调用方法--------------------------------
    public static ResponseDTO getSuccessResult() {
        return getSuccessResult(MsgCodeEnum.SUCCESS.getMessage());
    }

    public static ResponseDTO getSuccessDataResult(Object data) {
        return getResult(MsgCodeEnum.SUCCESS, data, MsgCodeEnum.SUCCESS.getMessage());
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static ResponseDTO getSuccessResult(String message) {
        return getResult(MsgCodeEnum.SUCCESS, null, message);
    }

    public static ResponseDTO getSuccessResult(int code, String message) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static ResponseDTO getFailureResult(MsgCodeEnum messageTypeEnum) {
        return getFailureResult(messageTypeEnum, messageTypeEnum.getMessage());
    }

    public static ResponseDTO getFailureResult(String message) {
        return getFailureResult(MsgCodeEnum.FAILURE, message);
    }

    public static ResponseDTO getFailureResult(MsgCodeEnum messageTypeEnum, String message) {
        return getResult(messageTypeEnum, null, message);
    }

    public static ResponseDTO getFailureResult(int code) {
        return getFailureResult(code, MsgCodeEnum.FAILURE.getMessage());
    }

    public static ResponseDTO getFailureResult(int code, String message) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    private static ResponseDTO getResult(MsgCodeEnum messageTypeEnum, Object data, String message) {
        ResponseDTO r = new ResponseDTO();
        r.setData(data);
        r.setCode(messageTypeEnum.getCode());
        r.setMessage(message);
        return r;
    }

    public boolean success() {
        return MsgCodeEnum.SUCCESS.getCode() == this.code;
    }


}
