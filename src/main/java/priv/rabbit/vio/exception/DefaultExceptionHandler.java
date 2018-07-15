package priv.rabbit.vio.exception;

import org.apache.shiro.authc.AccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.rabbit.vio.common.ResultInfo;


@RestControllerAdvice
public class DefaultExceptionHandler {

private static Logger LOG = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    // 捕捉 ShiroCustomRealm 抛出的异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo handleException(Exception ex) {
        LOG.error("》》》 handleException ：："+ex.getMessage() );
        return new ResultInfo(ResultInfo.FAILURE, ex.getMessage());
    }

    // 捕捉 ShiroCustomRealm 抛出的异常
    @ExceptionHandler(value = AccountException.class)
    @ResponseBody
    public ResultInfo handleShiroException(AccountException ex) {
        return new ResultInfo(ResultInfo.FAILURE, ex.getMessage());
    }

}
