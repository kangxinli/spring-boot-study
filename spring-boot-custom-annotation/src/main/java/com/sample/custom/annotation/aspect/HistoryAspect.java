package com.sample.custom.annotation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.sample.custom.annotation.entity.HistoryBo;

@Aspect
@Component
public class HistoryAspect {

    /**
     *  层切点
     */
    @Pointcut("@annotation(com.sample.custom.annotation.annotation.History)")
    public void controllerAspect(){

        System.out.println("***********层切点**************");
    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){

        System.out.println("***********前置通知**************");
    }

    /**
     * 后置通知
     * @param joinPoint
     */
    @After("controllerAspect()")
    public  void after(JoinPoint joinPoint) throws Exception{
        System.out.println("***********后置通知**************");

    }

    /**
     * 返回通知通知（返回通知）
     */
    @AfterReturning(returning = "ret",pointcut = "controllerAspect()")
    public void doAfterReturningGame(Object ret){

        HistoryBo historyBo = (HistoryBo)ret;
        System.out.println("***********返回通知**************: " + historyBo);
    }

}
