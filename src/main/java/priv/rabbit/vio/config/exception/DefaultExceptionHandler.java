package priv.rabbit.vio.config.exception;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.rabbit.vio.common.ResultInfo;


@RestControllerAdvice
public class DefaultExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(DefaultExceptionHandler.class);


    /**
     * 捕获参数异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo handleValidException(MethodArgumentNotValidException e){
        LOG.error("》》》 handleValidException");
        for (ObjectError objectError : e.getBindingResult().getAllErrors()){
            LOG.error("错误参数：："+objectError.getDefaultMessage());
        }
        return new ResultInfo(ResultInfo.FAILURE, e.getBindingResult().getFieldError().getDefaultMessage());
    }


    /**
     * 捕捉 ShiroCustomRealm 抛出的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo handleException(Exception ex) {
        ex.printStackTrace();
        LOG.error("》》》 handleException ：：" + ex.getMessage());
        return new ResultInfo(ResultInfo.FAILURE, ex.getMessage());
    }

    /**
     *  捕捉 ShiroCustomRealm 抛出的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = AccountException.class)
    @ResponseBody
    public ResultInfo handleShiroException(AccountException ex) {
        ex.printStackTrace();
        return new ResultInfo(ResultInfo.FAILURE, ex.getMessage());
    }

    /**
     * 捕获无权限异常
     *
     * @param e
     * @return
     * @date 2018年7月17日
     * @author LiMaoDa
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    private ResultInfo<Object> unauthorizedExceptionHandler(Exception e) {
        LOG.error("》》》 UnauthorizedException ：捕获无权限异常");
        if (LOG.isDebugEnabled()) {
            e.printStackTrace();
            LOG.error(JSON.toJSONString(e.getStackTrace()));
        }
        return new ResultInfo<Object>(ResultInfo.FAILURE, "您没有权限访问！");
    }

}
