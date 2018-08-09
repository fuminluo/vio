package priv.rabbit.vio.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import priv.rabbit.vio.common.LoggerInfo;
import priv.rabbit.vio.utils.JsonFormatUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        try {
            Signature signature = joinPoint.getSignature();
            HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference("request");
            long beginTime = System.currentTimeMillis();
            request.setAttribute("beginTime", beginTime);
            LoggerInfo loggerInfo = new LoggerInfo();
            loggerInfo.setTimestamp(beginTime);
            loggerInfo.setMethod(request.getMethod());
            List<String> argList = new ArrayList();

            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    try {
                        //请求数据 解析是为 JSONObject，如果解析不成功 catch 进行下一步处理
                        String requestDate = JSONObject.toJSONString(args[i]);
                        JSONObject requestJson = JSONObject.parseObject(requestDate);
                        Object contentType = requestJson.get("contentType");
                        Object originalFilename = requestJson.get("originalFilename");
                        //如果是上传文件类型就跳出 "application/octet-stream"
                        if (null != contentType) {
                            argList.add(i+":文件名:"+originalFilename);
                            argList.add(i+":文件类型:"+contentType);
                            break;
                        }
                        for (Map.Entry<String, Object> entry : requestJson.entrySet()) {
                            argList.add(entry.getKey()+":"+entry.getValue().toString());
                        }
                    } catch (Exception e) {
                        try {
                            //请求数据 解析是为 JSONArray，如果解析不成功 catch 进行下一步处理
                            String requestDate = JSONObject.toJSONString(args[i]);
                            JSONArray requestJSONArray = JSONArray.parseArray(requestDate);
                            for (int j = 0; j < requestJSONArray.size(); ++j) {
                                argList.add(requestJSONArray.get(i).toString());
                            }
                        } catch (Exception e1) {
                            //请求数据 解析是为 String （form-data数据为字符串的形式），未知数据也转成String
                            argList.add(args[i].toString());
                        }

                    }
                }
            }
            loggerInfo.setParameters(argList);
            loggerInfo.setUrl(request.getRequestURI());
            loggerInfo.setSessionId(request.getSession().getId());
            LOGGER.info("[CLS] - " + signature.getDeclaringTypeName() + "." + signature.getName());
            LOGGER.info("[IN] - " + JsonFormatUtils.formatJson(JSON.toJSONString(loggerInfo)));
        } catch (Exception e) {

        }
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


    /**
     * 管理员登录方法的切入点
     */
    @Pointcut("execution(* priv.rabbit.vio.service.*.login(..))")
    public void loginCell() {
    }

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* priv.rabbit.vio.service.*.save(..))")
    public void insertCell() {
    }

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* priv.rabbit.vio.service.*.update(..))")
    public void updateCell() {
    }

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* priv.rabbit.vio.service.*.delete(..))")
    public void deleteCell() {
    }

    /**
     * 登录操作(后置通知)
     *
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "loginCell()", argNames = "joinPoint,object", returning = "object")
    public void loginLog(JoinPoint joinPoint, Object object) throws Throwable {
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = optionContent(joinPoint.getArgs(), methodName);

    }

    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "insertCell()", argNames = "joinPoint,object", returning = "object")
    public void insertLog(JoinPoint joinPoint, Object object) throws Throwable {

        //判断参数
        if (joinPoint.getArgs() == null) {
            // 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        String opContent = optionContent(joinPoint.getArgs(), methodName);
        LOGGER.info(">>>" + opContent);

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                //LOGGER.info(">>方法>>" + methodName + ">>参数>>" + args[i].toString());
            }
        }


    }

    /**
     * 管理员修改操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "updateCell()", argNames = "joinPoint,object", returning = "object")
    public void updateLog(JoinPoint joinPoint, Object object) throws Throwable {
        // Admin admin=(Admin)
        // request.getSession().getAttribute("businessAdmin");

        // 判断参数
        if (joinPoint.getArgs() == null) {
            // 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = optionContent(joinPoint.getArgs(), methodName);

    }

    /**
     * 删除操作
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "deleteCell()", argNames = "joinPoint,object", returning = "object")
    public void deleteLog(JoinPoint joinPoint, Object object) throws Throwable {
        // Admin admin=(Admin)
        // request.getSession().getAttribute("businessAdmin");
        // 判断参数
        if (joinPoint.getArgs() == null) {
            // 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();

        StringBuffer rs = new StringBuffer();
        rs.append(methodName);
        String className = null;
        for (Object info : joinPoint.getArgs()) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数，类型:" + className + "，值:(id:"
                    + joinPoint.getArgs()[0] + ")");
        }


    }

    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型:" + className + "，值:");
            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                // 判断是不是get方法
                if (methodName.indexOf("get") == -1) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                } catch (Exception e) {
                    continue;
                }
                // 将值加入内容中
                rs.append("(" + methodName + ":" + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        return rs.toString();
    }
}

