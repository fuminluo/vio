package priv.rabbit.vio.exception;

import org.apache.shiro.authc.AccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.rabbit.vio.common.ResultInfo;


@RestControllerAdvice
public class DefaultExceptionHandler {

    // 捕捉 ShiroCustomRealm 抛出的异常
    @ExceptionHandler(value = AccountException.class)
    @ResponseBody
    public ResultInfo handleShiroException(Exception ex) {
        return new ResultInfo(ResultInfo.FAILURE, ex.getMessage());
    }

}
