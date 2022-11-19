package com.cy.bookstore.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component  // 将当前类创建对象的维护交给spring容器管理
@Aspect // 将此类标记为切面类
public class TimeAspect {
    @Around("execution(* com.cy.bookstore.service.impl.*.*(..))") //*表示全部，..表示省略，这里表示监听所标识的路径
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();  // 这里表示执行Around中的文件
        long end = System.currentTimeMillis();
        System.out.println("方法耗时"+(end-start));
        return result;
    }
}
