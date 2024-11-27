package com.se.scrumflow.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class ControllerLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    /**
     * 定义切点，拦截所有 Controller 包中的方法
     */
    @Pointcut("execution(* com.se.scrumflow.controller..*(..))") // 修改为你的 Controller 包路径
    public void controllerMethods() {
    }

    /**
     * 在方法执行前拦截并打印信息
     */
    @Before("controllerMethods()")
    public void logMethodParams(JoinPoint joinPoint) {
        // 获取当前的 HTTP 请求
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // 获取访问的 URI
            String uri = request.getRequestURI();

            // 获取时间戳（这里由日志框架自动管理）
            String timestamp = String.format("%1$tF %1$tT.%1$tL", System.currentTimeMillis());

            // 获取线程名
            String threadName = Thread.currentThread().getName();

            // 获取方法签名和入参
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            String methodName = method.getName();
            Object[] args = joinPoint.getArgs();

            // 日志打印
            logger.info("{} [{}] {} - {} {} {}",
                    timestamp, threadName, "INFO", uri, methodName, Arrays.toString(args));
        }
    }
}
