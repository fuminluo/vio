package priv.rabbit.vio.config.aop;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import priv.rabbit.vio.config.annotation.Encrypt;

/**
 * 请求请求处理类（目前仅仅对requestbody有效）
 * 对加了@Decrypt的方法的数据进行解密密操作
 *
 * @Author administered
 * @Description
 * @Date 2019/3/30 21:04
 **/
@ControllerAdvice
public class EncryptResponseBodyAdvice<T> implements ResponseBodyAdvice<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        LOGGER.info("EncryptResponseBodyAdvice :: supports :: " + returnType.getMethod().isAnnotationPresent(Encrypt.class));
        return returnType.getMethod().isAnnotationPresent(Encrypt.class);
    }

    @Nullable
    @Override
    public T beforeBodyWrite(@Nullable T body, MethodParameter returnType, MediaType selectedContentType,
                             Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        String content = null;
        try {
            content = JSONObject.toJSONString(body);
            //TODO 加密处理
            LOGGER.info("EncryptResponseBodyAdvice :: " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) content;
    }


}