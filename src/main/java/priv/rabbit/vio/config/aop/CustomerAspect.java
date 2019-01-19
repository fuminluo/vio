package priv.rabbit.vio.config.aop;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.thymeleaf.util.ArrayUtils;
import priv.rabbit.vio.config.annotation.BussAnnotation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 自定义aop 切面
 *
 * @author LuoFuMin
 * @Before: 前置通知, 在方法执行之前执行，这个通知不能阻止连接点前的执行（除非它抛出一个异常）。
 * @After: 后置通知, 在方法执行之后执行（不论是正常返回还是异常退出）。
 * @AfterReturning:返回通知, 在方法正常返回结果之后执行 。
 * @AfterThrowing: 异常通知, 在方法抛出异常之后。
 * @Around: 包围一个连接点（join point）的通知，如方法调用。这是最强大的一种通知类型。
 * 环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它们自己的返回值或抛出异常来结束执行
 * @data 2018/8/24
 */
@Aspect
@Component
public class CustomerAspect {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerAspect.class);

    /**
     * 自定义注解切点：CustomAnnotation注解
     */
    @Pointcut("@annotation(priv.rabbit.vio.config.annotation.CustomAnnotation)")
    private void executeWork() {
    }

    /**
     * 保存操作日志切点：BussAnnotation
     */
    @Pointcut("@annotation(priv.rabbit.vio.config.annotation.BussAnnotation)")
    public void saveOption() {

    }


    /**
     * 环绕通知   拦截指定的切点，这里拦截joinPointForAddOne切入点所指定的addOne()方法
     */
    @Around(value = "saveOption()&& @annotation(annotation) &&args(object,..)", argNames = "joinPoint,annotation,object")
    public Object interceptorAddOne(ProceedingJoinPoint joinPoint, BussAnnotation annotation, Object object) throws Throwable {
        LOG.info("Aop start");
        LOG.info("moduleName:" + annotation.moduleName());
        LOG.info("option:" + annotation.option());
        Object result = null;
        try {
            // 记录操作日志...谁..在什么时间..做了什么事情..
            result = joinPoint.proceed();
        } catch (Exception e) {
            // 异常处理记录日志..log.error(e);
            throw e;
        }
        LOG.info("Aop end");
        return result;
    }

    /**
     * Before: 前置通知, 在方法执行之前执行，这个通知不能阻止连接点前的执行（除非它抛出一个异常）
     *
     * @param joinPoint
     * @throws ClassNotFoundException
     * @throws NotFoundException
     * @throws IOException
     */
    @Before("executeWork()")
    public void doBefore(JoinPoint joinPoint) throws ClassNotFoundException, NotFoundException, IOException {
        LOG.info("自定义注解aop切面：before");
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference("request");
        String url = request.getRequestURL().toString();
        LOG.info("url ：" + url);
        String token = request.getHeader("Authorization");
        Signature signature = joinPoint.getSignature();
        LOG.info("[CLS] - " + signature.getDeclaringTypeName() + "." + signature.getName());
        printParam(joinPoint);

    }


    /**
     * AfterReturning 返回通知, 在方法正常返回结果之后执行
     *
     * @param result
     */
    @AfterReturning(value = "executeWork()", returning = "result")
    public void AfterRunning(Object result) {
        LOG.info("自定义注解aop切面：AfterReturning = " + JSONObject.toJSONString(result));
    }

    /**
     * After: 后置通知, 在方法执行之后执行（不论是正常返回还是异常退出）
     */
    @After("executeWork()")
    public void after() {
        LOG.info("自定义注解aop切面：After");
    }


    /**
     * 输出参数
     *
     * @param joinPoint
     */
    private void printParam(JoinPoint joinPoint) {
        //Signature 包含了方法名、申明类型以及地址等信息
        String class_name = joinPoint.getTarget().getClass().getName();
        String method_name = joinPoint.getSignature().getName();
        //重新定义日志
        //LOG.info("类名 = ", class_name);
        //LOG.info("方法名 = ", method_name);
        //获取方法的参数值数组。
        Object[] method_args = joinPoint.getArgs();
        try {
            //获取方法参数名称
            String[] paramNames = getFieldsName(class_name, method_name);
            //打印方法的参数名和参数值
            logParam(paramNames, method_args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用javassist来获取方法参数名称
     *
     * @param class_name  类名
     * @param method_name 方法名
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }

    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    private void logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            LOG.info("该方法没有参数");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i];
            //参数值
            Object value = paramsArgsValue[i];
            if (!"bindingResult".equals(name)) {
                buffer.append(name + " = ");
                if (isPrimite(value.getClass())) {
                    buffer.append(value + "  ,");
                } else {
                    String data = "";
                    try {
                        data = JSONObject.toJSONString(value);
                    } catch (Exception e) {
                        data = "未知参数";
                    }
                    buffer.append(data + "  ,");
                }
            }
        }
        LOG.info("请求参数 = " + buffer.toString());
    }

    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */
    private boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }

}
