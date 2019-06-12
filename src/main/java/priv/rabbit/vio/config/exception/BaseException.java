package priv.rabbit.vio.config.exception;

/**
 * @Author administered
 * @Description
 * @Date 2019/6/12 22:21
 **/
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;


    public BaseException() {

    }

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
