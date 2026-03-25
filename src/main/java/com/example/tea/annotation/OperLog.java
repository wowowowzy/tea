package com.example.tea.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) // 注解作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
@Documented
public @interface OperLog {
    /**
     * 操作模块 例：用户管理
     */
    String module() default "";

    /**
     * 操作类型 例：新增、修改、删除
     */
    String type() default "";
}
