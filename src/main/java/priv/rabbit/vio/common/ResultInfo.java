package priv.rabbit.vio.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultInfo{

    public static final int SUCCESS = 200;
    public static final int FAILURE = 400;

    public static final String  MSG_SUCCESS = "成功";
    public static final String  MSG_FAILURE = "失败";

    private Integer code;
    private String message;
    private Object data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public ResultInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultInfo(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
