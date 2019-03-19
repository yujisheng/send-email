package com.soft863.sendmail.uitl;

/**
 * @ClassName SendMailException
 * @Author
 * @Date 2019/3/19
 */
public class SendMailException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 13000;

    public SendMailException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SendMailException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public SendMailException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public SendMailException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
