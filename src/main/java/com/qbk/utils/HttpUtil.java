package com.qbk.utils;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * @Author: quboka
 * @Date: 2018/9/14 14:43
 * @Description: http
 */
public class HttpUtil {

    /**
     * 错误集合
     */
    public static final List<String> ERROR_LIST = new ArrayList<>(8) ;

    /**
     * 1.未知错误
     */
    public static final String ERROR = "T1,未知错误:" ;
    /**
     * 2.协议异常 ProtocolException
     */
    public static final String PROTOCOL_ERROR = "T2,协议错误:" ;
    /**
     * 3.超时异常(链接、读取) SocketTimeoutException
     */
    public static final String TIMEOUT_ERROR = "T3,超时错误:" ;
    /**
     * 4.链接错误 ConnectException
     */
    public static final String CONNECT_ERROR = "T4,链接错误:" ;
    /**
     * 5.io异常 IOException
     */
    public static final String IO_ERROR = "T5,IO错误:" ;
    /**
     * 6.空指针 NullPointerException
     */
    public static final String NULL_ERROR = "T6,空指针错误:" ;
    /**
     * 7.url不完整  MalformedURLException
     */
    public static final String URL_ERROR = "T7,URL错误:" ;
    /**
     * 8.请求失败 非200
     */
    public static final String FAILURE = "T8,请求失败,HTTP Status-Code:" ;


    static {
        ERROR_LIST.add(ERROR);
        ERROR_LIST.add(PROTOCOL_ERROR);
        ERROR_LIST.add(TIMEOUT_ERROR);
        ERROR_LIST.add(CONNECT_ERROR);
        ERROR_LIST.add(IO_ERROR);
        ERROR_LIST.add(NULL_ERROR);
        ERROR_LIST.add(URL_ERROR);
        ERROR_LIST.add(FAILURE);
    }

    /**
     *链接超时：毫秒
     */
    private static final int CONNECT_TIMEOUT = 8000 ;


    /**
     * 读取超时：毫秒
     */
    private static final int READ_TIMEOUT = 50000 ;
    /**
     *  字符集
     */
    private static final String CHARSET_NAME = "UTF-8" ;


    /**
     * post 请求
     * @param requestUrl url
     * @param paramMap 参数
     */
    public static String sendPostByMap(String requestUrl, Map<String,Object> paramMap){
        if (null == paramMap) {
            return HttpUtil.sendPost(requestUrl,null);
        }
       StringBuffer requestbody = new StringBuffer();
        //拼接参数
        Set<Map.Entry<String,Object>> set = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            if(entry.getValue() != null){
                requestbody.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return HttpUtil.sendPost(requestUrl,requestbody.toString());
    }

    /**
     * post 请求
     * @param requestUrl  url
     * @param requestbody 参数：name1=value1&name2=value2
     */
    public static String sendPost(String requestUrl, String requestbody){
        if( null == requestbody){
            requestbody = "" ;
        }
        BufferedReader responseReader = null ;
        BufferedWriter writer = null ;
        try {
            //建立连接
            URL url = new URL(requestUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //设置连接属性
            //使用URL连接进行输出
            connection.setDoOutput(true);
            //使用URL连接进行输入
            connection.setDoInput(true);
            //忽略缓存
            connection.setUseCaches(false);
            //设置URL请求方法
            connection.setRequestMethod("POST");

            // 维持长连接
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            //链接超时
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            //读取超时
            connection.setReadTimeout(READ_TIMEOUT);

            //建立输出流,并写入数据
            writer = new BufferedWriter( new PrintWriter( connection.getOutputStream()));
            writer.write(requestbody);
            writer.flush();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            //连接成功
            if (HttpURLConnection.HTTP_OK == responseCode) {
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHARSET_NAME));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                //成功 200
                return buffer.toString();
            }else{
                //其他
                return FAILURE + responseCode;
            }
        } catch (ProtocolException e) {
            return PROTOCOL_ERROR + e;
        } catch (SocketTimeoutException e) {
            return TIMEOUT_ERROR + e;
        } catch (MalformedURLException e) {
            return URL_ERROR + e;
        }  catch (ConnectException e) {
            return CONNECT_ERROR + e;
        } catch (IOException e) {
            return IO_ERROR + e;
        }catch (NullPointerException e){
            return NULL_ERROR + e ;
        }catch (Exception e){
            return ERROR + e ;
        }finally {
            try {
                if (writer != null){
                    writer.close();
                }
                if (responseReader != null){
                    responseReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * post json请求
     * @param requestUrl  url
     * @param object 参数
     */
    public static String sendPostByJson(String requestUrl, Object object){
        BufferedReader responseReader = null ;
        BufferedWriter writer = null ;
        try {
            //建立连接
            URL url = new URL(requestUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //设置连接属性
            //使用URL连接进行输出
            connection.setDoOutput(true);
            //使用URL连接进行输入
            connection.setDoInput(true);
            //忽略缓存
            connection.setUseCaches(false);
            //设置URL请求方法
            connection.setRequestMethod("POST");

            // 维持长连接
            connection.setRequestProperty("Connection", "Keep-Alive");
            //JSON
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");

            //链接超时
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            //读取超时
            connection.setReadTimeout(READ_TIMEOUT);

            //建立输出流,并写入数据
            writer = new BufferedWriter( new PrintWriter( connection.getOutputStream()));
            writer.write(JSON.toJSONString(object));
            writer.flush();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            //连接成功
            if (HttpURLConnection.HTTP_OK == responseCode) {
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHARSET_NAME));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                //成功 200
                return buffer.toString();
            }else{
                //其他
                return FAILURE + responseCode;
            }
        } catch (ProtocolException e) {
            return PROTOCOL_ERROR + e;
        } catch (SocketTimeoutException e) {
            return TIMEOUT_ERROR + e;
        } catch (MalformedURLException e) {
            return URL_ERROR + e;
        }  catch (ConnectException e) {
            return CONNECT_ERROR + e;
        } catch (IOException e) {
            return IO_ERROR + e;
        }catch (NullPointerException e){
            return NULL_ERROR + e ;
        }catch (Exception e){
            return ERROR  + e;
        }finally {
            try {
                if (writer != null){
                    writer.close();
                }
                if (responseReader != null){
                    responseReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *  get请求
     * @param requestUrl url+参数
     */
    public static String sendGet(String requestUrl){
        BufferedReader responseReader = null ;
        try{
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            //链接超时
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            //读取超时
            connection.setReadTimeout(READ_TIMEOUT);

            connection.connect();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            //连接成功
            if (HttpURLConnection.HTTP_OK == responseCode) {
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHARSET_NAME));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                //成功 200
                return buffer.toString();
            }else{
                //其他
                return FAILURE + responseCode ;
            }
        } catch (ProtocolException e) {
            return PROTOCOL_ERROR + e;
        } catch (SocketTimeoutException e) {
            return TIMEOUT_ERROR + e;
        } catch (MalformedURLException e) {
            return URL_ERROR + e;
        }  catch (ConnectException e) {
            return CONNECT_ERROR + e;
        } catch (IOException e) {
            return IO_ERROR + e;
        }catch (NullPointerException e){
            return NULL_ERROR  + e;
        }catch (Exception e){
            return ERROR  + e;
        }finally {
            try {
                if (responseReader != null){
                    responseReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *  校验结果(只校验是否请求成功)
     * @param result
     * @return
     */
    public static boolean checkoutResult (String result){
        for (String error : HttpUtil.ERROR_LIST) {
            boolean contains = result.contains(error);
            if(contains){
                return false ;
            }
        }
        return true ;
    }

    /**
     *  获取 url 中的ip
     * @param urlStr url
     * @return
     */
    public static String getIpByUrl(String urlStr){
        if (StringUtil.isEmpty(urlStr)){
            return "" ;
        }
        return urlStr.replaceFirst(RegexConstant.URL_CUT_REGEX,RegexConstant.CUT_IP);
    }

    /**
     *  获取 url 中的端口
     * @param urlStr url
     * @return port
     */
    public static Integer getPortByUrl(String urlStr){
        if (StringUtil.isEmpty(urlStr)){
            return null ;
        }
        return Integer.valueOf(urlStr.replaceFirst(RegexConstant.URL_CUT_REGEX,RegexConstant.CUT_PORT));
    }
    /**
     * 测试
     */
	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/remoteservice/user/login.do";
		Map<String,Object> paramMap = new HashMap<String,Object>(2);
		paramMap.put("loginName", "admin");
		paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
		String result1 = sendPostByMap(url, paramMap);
		String result2 = sendPost(url, "loginName=admin&password=e10adc3949ba59abbe56e057f20f883e");
		String result3 = sendGet(url+ "?loginName=admin&password=e10adc3949ba59abbe56e057f20f883e");
		String result4 = sendPostByJson(url, paramMap);
		System.out.println(result1);
		System.out.println(result2);
		System.out.println(result3);
		System.out.println(result4);
		//校验
        final boolean b = checkoutResult(result4);
        System.out.println(b);

	}




}
