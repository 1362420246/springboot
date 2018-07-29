package com.qbk;

import com.alibaba.fastjson.JSONObject;
import com.qbk.bean.UserBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

/**
 *RestTemplate 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTests {

    @Autowired
    private RestTemplate restTemplate ;

   // GET请求测试
    @Test
    public void get() throws Exception {
        //无参
        Map map = restTemplate.getForEntity("http://localhost:8080/remoteservice/api/accept.do", Map.class ).getBody();
        System.out.println("get请求无参数："+map);

        //有参
        Map<String,String> multiValueMap = new HashMap<>();
        multiValueMap.put("loginName","zhanghao");//传值，但要在url上配置相应的参数
        multiValueMap.put("password","e10adc3949ba59abbe56e057f20f883e");//传值，但要在url上配置相应的参数
        JSONObject json2 = restTemplate.getForEntity("http://localhost:8080/remoteservice/user/login.do?loginName={loginName}&password={password}", JSONObject.class ,multiValueMap).getBody();
        System.out.println("get请求带参数："+json2);

        //ResponseEntity code 判断
        /*ResponseEntity 是在 org.springframework.http.HttpEntity 的基础上添加了http status code(http状态码)，
        用于RestTemplate以及@Controller的HandlerMethod。它在Controoler中或者用于服务端响应时，作用是和@ResponseStatus与@ResponseBody结合起来的功能一样的。
        用于RestTemplate时，它是接收服务端返回的http status code 和 reason的。*/
        ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity("http://localhost:8080/remoteservice/api/accept.do", JSONObject.class);
        HttpStatus statusCode = forEntity.getStatusCode();
        if (HttpStatus.OK.equals(statusCode)) {
            JSONObject body = forEntity.getBody();
            if (body.getBoolean("result")) {
                String data = body.getString("msg");
                System.out.println("get请求："+data);
            }
        }
    }
    // POST请求测试
//    @Test
//    public void post() throws Exception {
//        MultiValueMap multiValueMap = new LinkedMultiValueMap();
//        multiValueMap.add("username","lake");
//        ActResult result = restTemplate.postForObject("/test/post",multiValueMap,ActResult.class);
//        Assert.assertEquals(result.getCode(),0);
//    }

//    文件上传测试
//    @Test
//    public void upload() throws Exception {
//        Resource resource = new FileSystemResource("/home/lake/github/wopi/build.gradle");
//        MultiValueMap multiValueMap = new LinkedMultiValueMap();
//        multiValueMap.add("username","lake");
//        multiValueMap.add("files",resource);
//        ActResult result = testRestTemplate.postForObject("/test/upload",multiValueMap,ActResult.class);
//        Assert.assertEquals(result.getCode(),0);
//    }
//    文件下载测试
//    @Test
//    public void download() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("token","xxxxxx");
//        HttpEntity formEntity = new HttpEntity(headers);
//        String[] urlVariables = new String[]{"admin"};
//        ResponseEntity<byte[]> response = testRestTemplate.exchange("/test/download?username={1}",HttpMethod.GET,formEntity,byte[].class,urlVariables);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            Files.write(response.getBody(),new File("/home/lake/github/file/test.gradle"));
//        }
//    }
//    请求头信息测试
//    @Test
//    public void getHeader() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("token","xxxxxx");
//        HttpEntity formEntity = new HttpEntity(headers);
//        String[] urlVariables = new String[]{"admin"};
//        ResponseEntity<ActResult> result = testRestTemplate.exchange("/test/getHeader?username={username}", HttpMethod.GET,formEntity,ActResult.class,urlVariables);
//        Assert.assertEquals(result.getBody().getCode(),0);
//    }
//    PUT请求测试
//    @Test
//    public void putHeader() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("token","xxxxxx");
//        MultiValueMap multiValueMap = new LinkedMultiValueMap();
//        multiValueMap.add("username","lake");
//        HttpEntity formEntity = new HttpEntity(multiValueMap,headers);
//        ResponseEntity<ActResult> result = testRestTemplate.exchange("/test/putHeader", HttpMethod.PUT,formEntity,ActResult.class);
//        Assert.assertEquals(result.getBody().getCode(),0);
//    }
//    DELETE请求测试
//    @Test
//    public void delete() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("token","xxxxx");
//        MultiValueMap multiValueMap = new LinkedMultiValueMap();
//        multiValueMap.add("username","lake");
//        HttpEntity formEntity = new HttpEntity(multiValueMap,headers);
//        String[] urlVariables = new String[]{"admin"};
//        ResponseEntity<ActResult> result = testRestTemplate.exchange("/test/delete?username={username}", HttpMethod.DELETE,formEntity,ActResult.class,urlVariables);
//        Assert.assertEquals(result.getBody().getCode(),0);
//    }
//    异步请求
//- 异步调用要使用AsyncRestTemplate。 它是RestTemplate的扩展，提供了异步http请求处理的一种机制，通过返回ListenableFuture对象生成回调机制，以达到异步非阻塞发送http请求
//    public String asyncReq(){
//        String url = "http://localhost:8080/jsonAsync";
//        ListenableFuture<ResponseEntity<JSONObject>> future = asyncRestTemplate.getForEntity(url, JSONObject.class);
//        future.addCallback(new SuccessCallback<ResponseEntity<JSONObject>>() {
//            public void onSuccess(ResponseEntity<JSONObject> result) {
//                System.out.println(result.getBody().toJSONString());
//            }
//        }, new FailureCallback() {
//            public void onFailure(Throwable ex) {
//                System.out.println("onFailure:"+ex);
//            }
//        });
//        return "this is async sample";
//    }

}
