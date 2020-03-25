package com.telekom.whatsapp.webhook.services;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//TODO
@Aspect
@Component
public class LoggingHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(
        "within(@org.springframework.stereotype.Controller *) && " +
        "execution(* *(..))"
    )
    public void allController() {};

    @Before("allController()")
    public void logMethodCall(JoinPoint jp) {

        logger.info("Received Request");
    }
}