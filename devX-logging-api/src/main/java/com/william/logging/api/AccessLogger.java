/*
 *
 *  * Copyright 2016 http://www.hswebframework.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.william.logging.api;

import com.william.logging.api.enums.SysLogType;

import java.lang.annotation.*;

/**
 * 访问日志,在类或者方法上注解此类,将对方法进行访问日志记录
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AccessLogger {

    /**
     * @return 模块说明
     * @see AccessLoggerInfo#getAction()
     */
    String module();

    /**
     * @return 对类或方法的详细描述
     * @see AccessLoggerInfo#getDescribe()
     */
    String[] describe() default "";

    /**
     * 日志类型 1-登陆 2-访问 3-操作 4-异常
     *
     * @return
     */
    SysLogType type() default SysLogType.AECCESS;

    /**
     * @return 是否取消日志记录, 如果不想记录某些方法或者类, 设置为true即可
     */
    boolean ignore() default false;
}
