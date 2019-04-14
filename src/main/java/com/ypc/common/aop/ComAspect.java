package com.ypc.common.aop;


import com.alibaba.fastjson.JSONObject;
import com.ypc.common.pojo.TokenModel;
import com.ypc.common.service.TokenManagerService;
import com.ypc.common.utils.ReflectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: ypc
 * @Date: 2018/4/15 14:57
 * @Description: aop记录运行日志
 * @Modified By:
 */

/*@Component
@Aspect*/
public class ComAspect implements Ordered{
    //private static final Logger logger = Logger.getLogger("MyLogAspect");
    private static final Logger logger = LogManager
            .getLogger(ComAspect.class);
    /**不需要拦截的路径*/
    public static final List<String> excludeUrls ;
    static {
        excludeUrls = new ArrayList<>();
        excludeUrls.add("/file/upload");
        excludeUrls.add("/file/uploadFile");
        excludeUrls.add("/down");
    }


    @Autowired
    private TokenManagerService tokenManagerService;
    // 切入点表达式按需配置
    @Pointcut("execution(* com.ypc..controller.*.*(..)) " +
            " && !execution(* com.ypc..CollectionController.*(..))")
    public void controllerPointcut() {
    }




    /**
     * 前置通知：在目标方法执行前执行
     * @param joinPoint
     */
    /*@Before("controllerPointcut()")
    public void beforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.warn(className + "的" + methodName + "执行了");
        Object[] args = joinPoint.getArgs();
        StringBuilder log = new StringBuilder("入参为");
        for (Object arg : args) {
            log.append(arg + " ");
        }
        logger.warn(log.toString());
        logger.info("在目标方法执行前执行");
    }*/




/**
     * 后置通知：在目标方法执行后执行
      *//*

    */
/*
    @After("controllerPointcut()")
    public void afterMethod() {

    }
    *//*


    */
/**
     *
     * 环绕通知：在目标方法执行前后都执行
     * @throws Throwable
     */

    @Around("controllerPointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object[] args = pjp.getArgs();
        try {
            String token = request.getParameter("token");
            if (StringUtils.hasText(token)) {
                TokenModel tokenModel = tokenManagerService.getTokenModel(token);
                if(tokenModel != null && args != null && args.length == 2 && args[1] != null){
                    if (request.getMethod().equals("GET")) {
                        Class<?> clazz = args[1].getClass() ;
                        if (clazz != null && clazz != Integer.class && clazz != String.class
                                && clazz != Float.class &&  clazz != Double.class
                                && clazz != Long.class) {
                            ReflectionUtils.setFieldValue(args[1],"currentUserId",tokenModel.getUserId());
                            if (StringUtils.hasText(tokenModel.getCompanyId()) && !tokenModel.getCompanyId().equals("0")) {
                                ReflectionUtils.setFieldValue(args[1],"companyId",tokenModel.getCompanyId());
                            }
                            if (StringUtils.hasText(tokenModel.getFactoryId()) && !tokenModel.getFactoryId().equals("0")) {
                                ReflectionUtils.setFieldValue(args[1],"factoryId",tokenModel.getFactoryId());
                            }
                            if (StringUtils.hasText(tokenModel.getRoleStr())) {
                                ReflectionUtils.setFieldValue(args[1],"currentUserRoleId",tokenModel.getRoleStr());
                            }
                        }
                    }else if(request.getMethod().equals("POST") && !excludeUrls.contains(request.getRequestURI())){
                        JSONObject jsonObject = JSONObject.parseObject(args[1].toString());
                        jsonObject.put("createBy",tokenModel.getUserId());
                        jsonObject.put("createByName",tokenModel.getUserName());
                        jsonObject.put("updateBy",tokenModel.getUserId());
                        jsonObject.put("updateByName",tokenModel.getUserName());
                            if (StringUtils.hasText(tokenModel.getRoleStr())) {
                            jsonObject.put("currentUserRoleId",tokenModel.getRoleStr());
                        }
                        if (StringUtils.hasText(tokenModel.getCompanyId()) && !tokenModel.getCompanyId().equals("0")) {
                            jsonObject.put("companyId",tokenModel.getCompanyId());
                        }
                        if (StringUtils.hasText(tokenModel.getFactoryId()) && !tokenModel.getFactoryId().equals("0")) {
                            jsonObject.put("factoryId",tokenModel.getFactoryId());
                        }
                        if (StringUtils.hasText(tokenModel.getSmallId()) && !tokenModel.getSmallId().equals("0")) {
                            jsonObject.put("smallId",tokenModel.getSmallId());
                        }
                        jsonObject.put("companyCode",tokenModel.getCompanyCode());
                        jsonObject.put("companyName",tokenModel.getCompanyName());
                        jsonObject.put("factoryCode",tokenModel.getFactoryCode());
                        jsonObject.put("factoryName",tokenModel.getFactoryName());
                        args[1] = jsonObject.toJSONString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object result = pjp.proceed(args);
        return result;
    }



    @Override
    public int getOrder() {
        return 2;
    }
}

