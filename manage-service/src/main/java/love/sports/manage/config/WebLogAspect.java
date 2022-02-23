package love.sports.manage.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class WebLogAspect {


    @Resource
    private ObjectMapper jacksonObjectMapper;

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final ThreadLocal<WebHttpInfo> webHttpInfoLocal = new ThreadLocal<>();

    @Pointcut("execution(public * com.love.sports.play.controller.*.*(..))")
    public void webLog() {
    }


    @Before(value = "webLog()")
    public void doBefore(JoinPoint point) {

        startTime.remove();
        webHttpInfoLocal.remove();

        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        WebHttpInfo webHttpInfo = new WebHttpInfo(request.getRemoteAddr(), request.getRequestURL().toString(), request.getMethod(), Arrays.toString(point.getArgs()));
        webHttpInfoLocal.set(webHttpInfo);

    }


    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws JsonProcessingException {
        WebHttpInfo webHttpInfo = webHttpInfoLocal.get();
        webHttpInfo.setRes(ret);
        webHttpInfo.setSpendTime(System.currentTimeMillis() - startTime.get());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        webHttpInfo.setUsername(userDetails.getUsername());
        startTime.remove();
        webHttpInfoLocal.remove();
        log.info(jacksonObjectMapper.writeValueAsString(webHttpInfo));
    }


    @Getter
    @Setter
    public static class WebHttpInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private Object args;
        private Object res;
        private long spendTime;
        private String username;


        public WebHttpInfo(String ip, String url, String httpMethod, Object args) {
            this.ip = ip;
            this.url = url;
            this.httpMethod = httpMethod;
            this.args = args;
        }
    }
}
