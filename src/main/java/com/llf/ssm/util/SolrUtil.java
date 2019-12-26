package com.llf.ssm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.params.MapSolrParams;
import org.json.JSONArray;

/**
 * @author: 爱不留
 * @date: 2018年8月27日 上午10:14:56
 * @Description:
 */
public class SolrUtil {

	public static String COLLECTION_ARTICLE = "article";
	public static String COLLECTION_RULE = "rule";
	public static String COLLECTION_LAW = "law";
//192.168.1.21:2181,192.168.1.22:2182
//	#server.1=47.106.155.20:2881:3881
//			#server.2=120.79.92.103:2882:3882
	//private static List<String> zks = Arrays.asList(new String[] { "47.106.155.20:2181", "120.79.92.103:2181"});
	//private static List<String> zks = Arrays.asList(new String[] { "192.168.1.21:2181", "192.168.1.22:2182"});
	//private static List<String> zks = Arrays.asList(new String[] { "http://192.168.1.21:8080/solr", "http://192.168.1.22:8080/solr" });
	private static List<String> zks = new ArrayList<String>();
	private static String choot = "/solr";	
	private static SolrClient solr = null;
	
	private static com.llf.ssm.util.SystemConfig systemConfig = com.llf.ssm.util.SpringContextUtil.getBean("systemConfig");

	static {
		// Optional<String> chootOp = Optional.of(choot);
		zks = Arrays.asList(systemConfig.getSolrzks().split(","));
		solr = new CloudSolrClient.Builder(zks, Optional.empty()).withConnectionTimeout(10000).withSocketTimeout(60000).build();
		
		
//		zks = Arrays.asList(systemConfig.getSolrzks().split(","));
//		solr = new CloudSolrClient.Builder(zks).withConnectionTimeout(10000).withSocketTimeout(60000).build();
	}

	// 查找 
//		public static <T> PageInfo<T> queryPage(String q, String sort, Integer pageNo, Integer pageSize, String collection, Class<T> clazz)
//				throws Exception {
//
//			List<T> list = new ArrayList<T>();
//
//			final Map<String, String> queryParamMap = new HashMap<String, String>();
//			queryParamMap.put("q", q);
//			if (StringUtils.isNoneBlank(sort)) {
//				queryParamMap.put("sort", sort);
//			}
//			queryParamMap.put("start", (Math.max(pageNo, 1) - 1) * pageSize + "");
//			queryParamMap.put("rows", pageSize + "");
//			MapSolrParams queryParams = new MapSolrParams(queryParamMap);
//			final QueryResponse response = solr.query(collection, queryParams);
//			long total = response.getResults().getNumFound();
//
//			String className = new StringBuilder().append(Character.toUpperCase(collection.charAt(0)))
//					.append(collection.substring(1)).toString();
//			Class clazzSolr = Class.forName("com.llf.ssm.bean." + className);
//
//			List listSolr = response.getBeans(clazzSolr);
//
//			String listSolrJsonStr = com.llf.ssm.util.JsonUtil.objToJsonString(listSolr);
//			//listSolrJsonStr = listSolrJsonStr.replaceAll("isTemplate", "isTemplet");
//			JSONArray array = new JSONArray(listSolrJsonStr);
//			list = com.llf.ssm.util.JsonUtil.listStr3ListObj(array, clazz);
//
//			PageInfo<T> t = new PageInfo<T>(list);
//			t.setPageNum(pageNo);
//			t.setPageSize(pageSize);
//			int pages = 1;
//			if(total % pageSize == 0) {
//				pages = (int) total / pageSize;
//			} else {
//				pages = (int) total / pageSize + 1;
//			}
//			t.setTotal(total);
//			t.setPages(pages);
//
//			return t;
//		}
		
	// 查找 
	public static List query(String q, String sort, Integer pageNo, Integer pageSize, String collection)
			throws Exception {
		final Map<String, String> queryParamMap = new HashMap<String, String>();
		queryParamMap.put("q", q);

		if (StringUtils.isNoneBlank(sort)) {
			queryParamMap.put("sort", sort);
		}

		queryParamMap.put("start", (Math.max(pageNo, 1) - 1) * pageSize + "");
		queryParamMap.put("rows", pageSize + "");

		MapSolrParams queryParams = new MapSolrParams(queryParamMap);

		final QueryResponse response = solr.query(collection, queryParams);

		String className = new StringBuilder().append(Character.toUpperCase(collection.charAt(0)))
				.append(collection.substring(1)).toString();
		Class clazz = Class.forName("com.llf.ssm.bean." + className);
		List list = response.getBeans(clazz);

		return list;
	}

	// 新增、更新
	public static Integer save(Object o, String collection) throws Exception {
		UpdateResponse response = execute(o, collection);
		return response.getStatus();
	}

	// 根据id删除
	public static Integer delete(String id, String collection) throws Exception {
		UpdateResponse response = executeDelete(id, collection);
		return response.getStatus();
	}

	public static UpdateResponse execute(Object o, String collection) throws Exception {
		UpdateResponse response = solr.addBean(collection, o);
		return response;
	}

	public static UpdateResponse executeDelete(String id, String collection) throws Exception {
		UpdateResponse response = solr.deleteById(collection, id);
		return response;
	}


	
	/**
	 * 使用模式： 
	 * 1、案由和关键词都有值，案由 AND 关键词
	 * 2、案由null，只用关键词匹配所有的字段
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getQ (Map paramMap) throws UnsupportedEncodingException {
		String q = "";	
		
		String legalCase = paramMap.get("legalCase") == null ? null : paramMap.get("legalCase").toString();
		String docTitle = paramMap.get("docTitle") == null ? null : paramMap.get("docTitle").toString();
		String content = paramMap.get("content") == null ? null : paramMap.get("content").toString();
		String sourceLegalCaseId = paramMap.get("sourceLegalCaseId") == null ? null : paramMap.get("sourceLegalCaseId").toString();
		String keyWord = paramMap.get("keyWord") == null ? null : paramMap.get("keyWord").toString();
		Boolean isTemplet = paramMap.get("isTemplet") == null ? false : Boolean.valueOf(paramMap.get("isTemplet").toString());				

		String sourceLegalCaseIdStr = "";
		if(sourceLegalCaseId != null) {
			String[] strs = sourceLegalCaseId.split(" ");
			if(strs.length > 0) {
				for(int i = 0; i < strs.length; i ++) {
		            if(i == 0) {
		            	sourceLegalCaseIdStr = "sourceLegalCaseId:" + strs[i];
		            } else {
		            	sourceLegalCaseIdStr = sourceLegalCaseIdStr + " OR " + "sourceLegalCaseId:" + strs[i];
		            }
		        }
			}
		}
		String keyStr = "";
		if(keyWord != null) {
			String[] strs = keyWord.split(" ");
			if(strs.length > 0) {
				for(int i = 0; i < strs.length; i ++) {
		            if(i == 0) {
		                keyStr = "keyWord:" + strs[i];
		            } else {
		                keyStr = keyStr + " OR " + "keyWord:" + strs[i];
		            }
		        }
			}
		}
		String docTitleStr = "";
		if(docTitle != null) {
			String[] strs = docTitle.split(" ");
			if(strs.length > 0) {
				for(int i = 0; i < strs.length; i ++) {
		            if(i == 0) {
		            	docTitleStr = "docTitle:" + strs[i];
		            } else {
		            	docTitleStr = docTitleStr + " OR " + "docTitle:" + strs[i];
		            }
		        }
			}
		}
		String contentStr = "";
		if(content != null) {
			String[] strs = content.split(" ");
			if(strs.length > 0) {
				for(int i = 0; i < strs.length; i ++) {
		            if(i == 0) {
		            	contentStr = "content:" + strs[i];
		            } else {
		            	contentStr = contentStr + " OR " + "content:" + strs[i];
		            }
		        }
			}
		}
		// legalCase:   and  (keyStr) and (docTitleStr)  and (connectStr)		() AND (  )
		if(legalCase != null && legalCase != "" && !"null".equals(legalCase) && !"undefined".equals(legalCase)) {			
			q = "legalCase:" + legalCase + " AND " + "(" + keyStr + ")" + " AND " + "(" + docTitleStr + ")" + " AND " + "(" + contentStr + ")" + " AND " + "(" + sourceLegalCaseIdStr + ")";			
		} else {
			q = "(" + keyStr + ")" + " AND " + "(" + docTitleStr + ")" + " AND " + "(" + contentStr + ")" + " AND " + "(" + sourceLegalCaseIdStr + ")";
		}
		q = q.replaceAll(" AND \\(\\)", "");
		q = q.replaceAll("\\(\\) AND ", "");
		if(isTemplet != null && isTemplet) {
			// 只搜模板规则
			q = "(" + q + ")" +  " AND " + "isTemplet:" + 1 ;
		}
		if("()".equals(q)) {
			q = "*:*";
		}
		return q;
	}



	
}
