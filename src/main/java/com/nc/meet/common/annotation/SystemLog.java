package com.nc.meet.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author zigang
 */
@Target(ElementType.METHOD) //注解用在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String value() default "";
}
