package com.clouddisk.server.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
//自定义注解，该注解只是标记作用，标记当前请求路径。不影响业务逻辑
@Target(ElementType.METHOD)
public @interface RequestPath {
    String value();
}
