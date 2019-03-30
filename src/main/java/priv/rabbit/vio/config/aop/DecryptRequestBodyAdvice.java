package priv.rabbit.vio.config.aop;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import priv.rabbit.vio.config.annotation.Decrypt;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import static priv.rabbit.vio.config.aop.DecryptRequestBodyAdvice.LOGGER;

/**
 * 请求请求处理类（目前仅仅对requestbody有效）
 * 对加了@Decrypt的方法的数据进行解密密操作
 * 接口需要加 @RequestBody 才会进入此方法
 *
 * @Author administered
 * @Description
 * @Date 2019/3/27 22:36
 **/
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    static final Logger LOGGER = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        LOGGER.info(" 》》》" + methodParameter.getMethod().isAnnotationPresent(Decrypt.class));
        //这里设置成false 它就不会再走这个类了
        return methodParameter.getMethod().isAnnotationPresent(Decrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            return new DecryptHttpInputMessage(inputMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        return body;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    //这里实现了HttpInputMessage 封装一个自己的HttpInputMessage
    static class MyHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        public MyHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}

class DecryptHttpInputMessage implements HttpInputMessage {
    private HttpHeaders headers;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
        //获取请求内容   headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), "UTF-8");
        LOGGER.info("DecryptHttpInputMessage content :: " + content);
        Integer start = 2;
        Integer end = content.length() - 2;
        // 去除数组括号 【】
        String decryptBody = content.substring(start, end);
        //去转义符
        decryptBody = StringEscapeUtils.unescapeJava(decryptBody);
        LOGGER.info("decryptBody :: " + decryptBody);
        //TODO 解密
        this.body = IOUtils.toInputStream(decryptBody, "UTF-8");
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}