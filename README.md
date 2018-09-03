# SpringCloud-Feign 声明式服务调用
**运行说明**
先启动eureka,再启动order,product，访问eureka的首页，即`http://localhost:8761/eureka`，就能看到order,product服务已经注册到eureka上了。

在浏览器中输入`http://localhost:9080/getProductMsg`,如果正确的话，返回的是this is product message 2，说明整个应该已经成功了。

**程序说明**<br>
在Spring Cloud Feign的实现下，我们只需创建一个接口并用注解的方式来配置它，即可以完成对服务提供方的接口绑定。

**入门程序讲解**<br>
在order服务中，加入依赖包，spring-cloud-start-openfeign,并且在主类中orderApplication.java上添加@EnableFeignClients注解,来开启Spring Cloud Feign的支持功能
<br>
定义ProductClient接口，通过@FeignClient注解来指定服务名来绑定服务，这里绑定的是product的服务。然后使用SpringMVC的注解来绑定该服务提供的REST接口。
代码如下<br>
```Java

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/msg")
    String productMsg();
}

```
这里的路由与product服务中的要对应，才能访问上。product服务中如下<br>
```Java

@RestController
public class ServerController {

    @RequestMapping("/msg")
    public String getMsg(){
        System.out.println("111");
        return "this is product message 2";
    }
}
```

接着，在order中创建一个ClientController来实现对Feign客户端的调用，使用@Autowired直接注入上面定义的HelloService实例，并在浏览器中发起对/getProductMsg的调用
```Java

@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        String response = productClient.productMsg();
        log.info("response={}",response);
        return response;
    }

}
```
<br>
发送几次请求到`http://localhost:9080/getProductMsg，正确返回了this is product message 2.<br>
根据控制台的输出，我们可以看到Feign实现的消费者，依然利用的是Ribbon维护了针对product的服务列表信息，并且通过了轮询实现了客户端负载均衡，<br>
而与Ribbon不同的是，通过Feign我们只需定义服务绑定接口，以声明式的方式，优雅而简单地实现了服务调用。

