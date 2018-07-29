package com.qbk.config;

import com.qbk.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
//import org.thymeleaf.Thymeleaf;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.util.Map;

//使用 @EnableWebMvc 注解，需要以编程的方式指定视图文件相关配置；
//使用 @EnableAutoConfiguration(SpringBootApplication) 注解，会读取 application.properties 或 application.yml 文件中的配置。
//@EnableWebMvc 会让配置文件application.yml中的配置失效

//mvc配置
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //        /** 解决跨域问题 **/
//        //public void addCorsMappings(CorsRegistry registry) ;
//        /** 添加拦截器 **/
//        //void addInterceptors(InterceptorRegistry registry);
//        /** 这里配置视图解析器 **/
//        //void configureViewResolvers(ViewResolverRegistry registry);
//        /** 配置内容裁决的一些选项 **/
//        //void configureContentNegotiation(ContentNegotiationConfigurer configurer);
//        /** 视图跳转控制器 **/
//        // void addViewControllers(ViewControllerRegistry registry);
//        /** 静态资源处理 **/
//        //void addResourceHandlers(ResourceHandlerRegistry registry);
//        /** 默认静态资源处理器 **/
//        //void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);

    /********************************  拦截器   ***************************************/
    /**
     * 日志拦截器
     */
    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 配置添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则 ("/**")对所有请求都拦截
        // excludePathPatterns 排除拦截
        registry.addInterceptor(logInterceptor).addPathPatterns("/**").excludePathPatterns("/api/**", "/login");
    }
    /********************************  自定义视图跳转   ***************************************/
    //添加自定义视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 对 "/hello" 的 请求 redirect(重定向) 到 ""/api/getRequest"
        registry.addRedirectViewController("/redirect", "/api/getRequest");
        // 对 "/admin/**" 的请求 返回 403 的 http 状态
        registry.addStatusController("/admin/**", HttpStatus.FORBIDDEN);
        // 将 "/hi" 的 请求响应为返回 "hi" 的视图
        registry.addViewController("/hi").setViewName("/hi");
        registry.addViewController("/hello").setViewName("/hello");
    }

    /********************************  jsp模板配置   ***************************************/
    /**
     * 配置jsp视图映射
     */
    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("/jsp/");//webapp下
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".jsp");
        //若在项目中使用了JSTL,则SpringMVC会自动把视图由InternalResourceView转为JstlView
        internalResourceViewResolver.setViewClass(JstlView.class);
        return internalResourceViewResolver;
    }
    /**
     * 配置视图
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(resourceViewResolver());
    }
    /********************************  thymeleaf模板配置（html）   ***************************************/
    /**
     * 配置thymeleaf视图映射  如果导入thymeleaf pom ，jsp模板自动失效
     */
//    @Bean
//    public SpringResourceTemplateResolver templateResolver(){
//        // SpringResourceTemplateResolver自动与Spring自己集成
//        // 资源解决基础设施, 强烈推荐。
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setPrefix("/html/");
//        templateResolver.setSuffix(".html");
//        // HTML是默认值, 为了清楚起见, 在此处添加。
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        // 默认情况下, 模板缓存为true。如果您想要设置为false
//        // 模板在修改时自动更新。
//        templateResolver.setCacheable(true);
//        return templateResolver;
//    }
    //@Bean
//    public SpringTemplateEngine templateEngine(){
//        // SpringTemplateEngine自动应用SpringStandardDialect
//        // 并启用Spring自己的MessageSource消息解析机制。
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        // 使用Spring 4.2.4或更高版本启用SpringEL编译器
//        // 可以加快大多数情况下的执行速度, 但是当一个模板中
//        // 的表达式在不同数据类型之间重用时,
//        // 可能与特定情况不兼容, 因此该标志默认为“false”
//        // 以实现更安全的向后兼容性。
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
   // @Bean
//    public ThymeleafViewResolver viewResolver(){
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        // 注意“order”和“viewNames”是可选的
//        viewResolver.setOrder(1);
//        viewResolver.setViewNames(new String[] {".html", ".xhtml"});
//        return viewResolver;
//    }

    /********************************  静态资源映射配置   ***************************************/
    //配置静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指文件放置的目录
        //addResourceHandler指的是对外暴露的访问路径   位置：webapp下
        registry.addResourceHandler("/tupian/**").addResourceLocations("/images/");
        registry.addResourceHandler("/h/**").addResourceLocations("/html/");
    }


    /********************************  RestTemplate配置  ***************************************/
    /**
     *  TestRestTemplate 是用于 Restful 请求的模版,并支持异步调用，
     *  默认情况下 RestTemplate 依靠 JDK 工具来建立 HTTP 链接，
     *  你也可以通过 setRequestFactory 方法来切换不同的 HTTP 库，如 Apache 的 HttpComponents 或 Netty 和 OkHttp
     *  通常在入口类或配置类将其注入到IOC容器，它有两个构造方法
     */
   // 默认初始化
//    @Bean
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }
    //构造方法中可以传入ClientHttpRequestFactory参数,ClientHttpRequestFactory接口的实现类中存在timeout属性等等
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
//        return new RestTemplate(factory);
//    }

    /*    它主要提供了了以下方法，对应不同的 HTTP 请求
    HTTP  :   Method	RestTemplate Methods
    DELETE :	delete
    GET :	getForObject、getForEntity
    HEAD :	headForHeaders
    OPTIONS :	optionsForAllow
    POST : postForLocation、postForObject
    PUT :	put
    any	 :exchange、execute*/

    //  请求配置
    @Bean
    RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);//单位为ms
        requestFactory.setReadTimeout(5000);//单位为ms
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        //restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }





}
