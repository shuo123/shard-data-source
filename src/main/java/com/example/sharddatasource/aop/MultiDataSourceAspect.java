package com.example.sharddatasource.aop;

import com.example.sharddatasource.datasource.MultiDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 14:42
 **/
@Component
@Aspect
public class MultiDataSourceAspect {

    @Pointcut("execution(* com.example.sharddatasource.dao..*.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        if(name.startsWith("save")){
            MultiDataSourceHolder.setDataSourceKey(MultiDataSourceHolder.MASTER);
        }
        if(name.startsWith("find")){
            MultiDataSourceHolder.setDataSourceKey(MultiDataSourceHolder.SLAVE);
        }
    }

}
