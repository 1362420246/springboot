package com.qbk.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageInfo;


public class ResultUtils{
	//private static Logger logger = LogManager.getLogger(ResultUtils.class);
	
	public static <T> Map<String, Object> getResult(boolean result, String msg){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	
	public static <T> Map<String, Object> getResult(boolean result, String msg, PageInfo<T> pageInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("msg", msg);
		resultMap.put("resultList", pageInfo);
		return resultMap;
	}
	public static Map<String, Object>  getResult(boolean result, String msg, Object object) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("msg", msg);
		resultMap.put("data", object);
		return resultMap;
	}
}
