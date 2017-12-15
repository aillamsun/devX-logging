# devX-logging - Getting Started

devX-logging 封装spring boot aop log starter


## 1. Install with Maven 

> 安装


cd ../devX-logging

```bash
  mvn clean package
```



## 2. Add Dependency 

> 添加依赖

```java
  <dependency>
      <groupId>com.william</groupId>
      <artifactId>devX-logging-starter</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
```

## 3. Using `@EnableAccessLogger` on Main Class 

> 使用 Spring boot 启动类加 @EnableAccessLogger

```java
@EnableAccessLogger
@SpringApplication
public class YourApplication {
    public static void main(String[] args){
        SpringApplication.run(YourApplication.class, args);
    }
}
```

## 4. Testing #测试
> * devX-logging-samples
> * spring-boot:run

> * 访问http://localhost:8080/log


#### 4.1 Sample

**Main Class**

```java
@SpringBootApplication
/**
* 启动日志Aop
*/
@EnableAccessLogger
public class SamplesApp extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SamplesApp.class);
    }

    /**
     * spring boot 服务主入口
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SamplesApp.class, args);
    }
}
```

------

**Controller**

```java
@RestController
@RequestMapping("log")
public class TestController {


    @RequestMapping(method = RequestMethod.POST)
    @AccessLogger(module = "测试日志模块", describe = "测试登录", type = SysLogType.LOGIN)
    public String testLogin() {
        return "LOGIN";
    }

    @RequestMapping(method = RequestMethod.GET)
    @AccessLogger(module = "测试日志模块", describe = "测试访问", type = SysLogType.AECCESS)
    public String testAccess() {
        return "AECCESS";
    }


    @RequestMapping(method = RequestMethod.PUT)
    @AccessLogger(module = "测试日志模块", describe = "测试操作", type = SysLogType.OPER)
    public String testOper() {
        return "OPER";
    }
}
```
------

#### 4.2 Extension by Implementing `AccessLoggerListener`

> 继承 AccessLoggerListener

```java
@Component
public class AccessLog implements AccessLoggerListener {
    @Override
    public void onLogger(AccessLoggerInfo loggerInfo) {
        System.out.println(JSON.toJSONString(loggerInfo));
    }
}

```

### 5 A Return Sample

> 返回格式:

```json
{"action":"访问","describe":"测试访问","httpHeaders":{"host":"localhost:8080","connection":"keep-alive","cache-control":"max-age=0","user-agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36","upgrade-insecure-requests":"1","accept":"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8","accept-encoding":"gzip, deflate, br","accept-language":"zh-CN,zh;q=0.9,en;q=0.8","cookie":"Idea-92744ae7=c77d525f-75f6-4a90-892f-60b49798f026; olfsk=olfsk49921969428008994; hblid=vKo3049kW1NNZ4bW3m39N0H0RE2aDaCZ; shop_id=2; JSESSIONID=47321175CA9DE3494204BACCA95FAA3A"},"httpMethod":"GET","id":"6398e70971ad50cbc22b69714d5f78a4","ip":"0:0:0:0:0:0:0:1","method":{"accessible":false,"annotatedExceptionTypes":[],"annotatedParameterTypes":[],"annotatedReceiverType":{"annotations":[],"declaredAnnotations":[],"type":"com.william.logging.example.controller.TestController"},"annotatedReturnType":{"annotations":[],"declaredAnnotations":[],"type":"java.lang.String"},"annotations":[{},{}],"bridge":false,"declaringClass":"com.william.logging.example.controller.TestController","default":false,"exceptionTypes":[],"genericExceptionTypes":[],"genericParameterTypes":[],"genericReturnType":"java.lang.String","modifiers":1,"name":"testAccess","parameterAnnotations":[],"parameterCount":0,"parameterTypes":[],"returnType":"java.lang.String","synthetic":false,"typeParameters":[],"varArgs":false},"module":"测试日志模块","parameters":{},"requestTime":1510889315105,"response":"AECCESS","responseTime":1510889315126,"target":"com.william.logging.example.controller.TestController","url":"http://localhost:8080/log"}
```
