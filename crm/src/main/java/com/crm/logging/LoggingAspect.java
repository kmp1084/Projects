package com.crm.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.crm.entities.Customer;

@Component
@EnableAspectJAutoProxy
@Aspect
public class LoggingAspect {
	
	private Logger logger = Logger.getLogger(LoggingAspect.class.getName());
	
	@Pointcut("execution(public String showAllCustomers(*))")
	public void showAllCustomers() {}
	
	@Order(1)
	@Before("showAllCustomers())")
	public void logBeforeShowingAllLeads(JoinPoint joinPoint) {
		MethodSignature msig = (MethodSignature)joinPoint.getSignature();
		logger.log(Level.INFO, "method signature (@Before): " + msig);
		logger.log(Level.INFO, "About to show all existing leads");
	}
	
	@After("showAllCustomers())")
	public void logAfterShowingAllLeads(JoinPoint joinPoint) {
		MethodSignature msig = (MethodSignature)joinPoint.getSignature();
		logger.log(Level.INFO, "method signature (@After): " + msig);
		logger.log(Level.INFO, "All current leads should now be listed on the page");
	}
	
	@AfterThrowing(pointcut="execution(public String saveLead(*, *))", throwing="exce")
	public void logTheExceptionFromSaveLead(JoinPoint joinPoint, Throwable exce) {
		MethodSignature msig = (MethodSignature)joinPoint.getSignature();
		logger.log(Level.INFO, "method signature (@AfterThrowing): " + msig);
		logger.log(Level.INFO, "Exception: " + exce);
	}
	
	@AfterReturning(pointcut="execution(public String saveLead(*, *))", returning="path")
	public void logThePathAfterReturingFromSaveLead(JoinPoint joinPoint, String path) {
		MethodSignature msig = (MethodSignature)joinPoint.getSignature();
		logger.log(Level.INFO, "method signature (@AfterReturning): " + msig);
		logger.log(Level.INFO, "Path: " + path);
	}
	
	@After("execution(public String saveLead(*, *))")
	public void logParametesForSaveLead(JoinPoint joinPoint) {
		MethodSignature msig = (MethodSignature) joinPoint.getSignature();
		System.out.println("method signature (@After): " + msig);
		logger.log(Level.INFO, "method signature (@After): " + msig);
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof Customer) {
				Customer c = (Customer)arg;
				logger.log(Level.INFO, String.valueOf(c));
			}
		}
	}
}
