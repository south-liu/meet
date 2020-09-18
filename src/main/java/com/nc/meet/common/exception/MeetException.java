package com.nc.meet.common.exception;

/**
 * 自定义异常
 * @author zigang
 */
public class MeetException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public MeetException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public MeetException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public MeetException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public MeetException(String msg, int code, Throwable e) {
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
