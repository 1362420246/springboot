package com.qbk.advice;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//通过@ControllerAdvice，我们可以将控制器的全局配置放置在同一个位置，注解了@Controller的类的方法可使用@ExceptionHandler、@InitBinder、@ModelAttribute注解到方法上，这对所有注解了@RequestMapping的控制器内的方法有效。
//@ExceptionHandler：配置全局异常处理
//@InitBinder：配置WebDataBinder，自动绑定前台请求参数到Model中
//@ModelAttribute：@ModelAttribute本来的作用是绑定键值对到Model中，此处是让全局的@RequestMapping都能获得在此处设置的键值对。

//@ControllerAdvice声明了一个控制器增强   组合了@Component注解，所以自动注册为spring的bean
@ControllerAdvice
//返回JSON格式 过@ControllerAdvice统一定义不同Exception映射到不同错误处理页面。而当我们要实现RESTful API时，返回的错误是JSON格式的数据，而不是HTML页面，这时候我们也能轻松支持。
//只需在@ExceptionHandler之后加入@ResponseBody，就能让处理函数return的内容转换为JSON格式。
//@ResponseBody
//这里可以配置全局的控制器配置
public class ExceptionHandlerAdvice {

    public static final String DEFAULT_ERROR_VIEW = "/error";

    //统一异常页面
    //只需要在Controller中抛出Exception，当然我们可能会有多种不同的Exception。然后在@ControllerAdvice类中，根据抛出的具体Exception类型匹配@ExceptionHandler中配置的异常类型来匹配错误映射和处理。
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }



}
