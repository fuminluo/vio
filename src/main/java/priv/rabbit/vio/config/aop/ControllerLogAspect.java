package priv.rabbit.vio.config.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import priv.rabbit.vio.common.IBaseRequest;
import priv.rabbit.vio.common.LoggerInfo;
import priv.rabbit.vio.utils.JsonFormatUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author LuoFuMin
 * @data 2018/8/7
 */
@Aspect
@Component
public class ControllerLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);

    @Pointcut("execution(public * priv.*.*.controller.*.*(..))")
    public void executeService() {
    }

    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        HttpServletRequest request = (HttpServletRequest)RequestContextHolder.getRequestAttributes().resolveReference("request");
        String taskuuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        long beginTime = System.currentTimeMillis();
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("taskuuid", taskuuid);
        LoggerInfo loggerInfo = new LoggerInfo();
        loggerInfo.setTimestamp(beginTime);
        loggerInfo.setTaskuuid(taskuuid);
        loggerInfo.setMethod(request.getMethod());
        List<IBaseRequest> argList = new ArrayList();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for(int i = 0; i < args.length; ++i) {
                if (args[i] instanceof IBaseRequest) {
                    IBaseRequest arg = (IBaseRequest)args[i];
                    argList.add(arg);
                }
            }
        }
        loggerInfo.setParameters(argList);
        loggerInfo.setUrl(request.getRequestURI());
        loggerInfo.setSessionId(request.getSession().getId());
        LOGGER.info("[CLS] - " + signature.getDeclaringTypeName() + "." + signature.getName());
        LOGGER.info("[IN] - " + JsonFormatUtils.formatJson(JSON.toJSONString(loggerInfo)));
    }

    @AfterReturning(
            value = "execution(* priv.*.*.controller.*.*(..))",
            returning = "keys"
    )
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys) {
        try {
            HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference("request");
            long beginTime = Long.parseLong(String.valueOf(request.getAttribute("beginTime")));
            String taskuuid = String.valueOf(request.getAttribute("taskuuid"));
            long endTime = System.currentTimeMillis();
            LoggerInfo loggerInfo = new LoggerInfo();
            loggerInfo.setTimestamp(beginTime);
            loggerInfo.setDuration(endTime - beginTime);
            loggerInfo.setTaskuuid(taskuuid);
            loggerInfo.setResult(keys);
            LOGGER.info("[OUT] - " + JsonFormatUtils.formatJson(JSON.toJSONString(loggerInfo)));
        } catch (Exception e) {

        }
    }

    @AfterThrowing(
            value = "executeService()",
            throwing = "exception"
    )
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        LOGGER.info("[IN] - " + exception.getStackTrace());
    }


}

