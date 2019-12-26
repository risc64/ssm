package com.llf.ssm.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;


public class JsonUtil {
	
	public static String objToJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			result = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	public static <T> List<T> str2ListObj(String jsonListStr,Class<T> clazz,String separator){
		List<T> list = new ArrayList<T>();
		T obj;
		ObjectMapper mapper = new ObjectMapper();
		for (String json : jsonListStr.split(separator)) {
			try {
				obj = (T) mapper.readValue(json, clazz);
				list.add(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static <T> List<T> listStr2ListObj(List<String> jsonList,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		T obj;
		ObjectMapper mapper = new ObjectMapper();
		for (String json : jsonList) {
			try {
				obj = (T) mapper.readValue(json, clazz);
				list.add(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static <T> List<T> listStr2ListObj(JSONArray jsonArray,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		T obj;
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject json = jsonArray.getJSONObject(i);
				mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
				obj = (T) mapper.readValue(json.toString(), clazz);
				list.add(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static <T> List<T> listStr3ListObj(JSONArray jsonArray,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		T obj;
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject json = jsonArray.getJSONObject(i);
				//mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
				obj = (T) mapper.readValue(json.toString(), clazz);
				list.add(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
     *  Jackson library
     * @param jsonInString
     * @return
     */
    public final static boolean isJSONValid2(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
