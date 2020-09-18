package com.nc.meet.common.aspect;

import com.nc.meet.common.annotation.SystemLog;
import com.nc.meet.common.utils.HttpContextUtils;
import com.nc.meet.common.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面处理
 * @author zigang
 */
@Aspect
@Component
public class SystemLogAspect {

	@Pointcut("@annotation(com.nc.meet.common.annotation.SystemLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SystemLog syslog = method.getAnnotation(SystemLog.class);

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();

		//请求的参数
		Object[] args = joinPoint.getArgs();

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		IPUtils.getIpAddr(request);

		//用户名

		//保存系统日志
		System.out.println("保存日志");
	}
}
