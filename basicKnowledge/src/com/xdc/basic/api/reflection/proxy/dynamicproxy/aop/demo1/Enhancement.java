package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 有此注解的方法，才需要被增强
 * 
 * @author xdc
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Enhancement
{
}
