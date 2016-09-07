package com.tdActivity.android.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.tdActivity.android.entity.JsonDTO;

public class HttpUtil {
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static JSONObject Request(String url) throws Exception {
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(url);
		HttpClientParams.setCookiePolicy(httpClient.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static JSONObject getRequest(String url) throws Exception {
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(Constant.BASE_URL + url);
		HttpClientParams.setCookiePolicy(httpClient.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public synchronized static JSONObject postRequest(String url,
			Map<String, String> rawParams) throws Exception {
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(Constant.BASE_URL + url);
		System.out.println(Constant.BASE_URL + url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				5000);
		// 发送POST请求
		HttpResponse httpResponse = httpClient.execute(post);
		// /**
		// * //关闭post请求，多线程post请求时需要加此方法，不然会报
		// * Invalid use of SingleClientConnManager: connection still allocated.
		// * Make sure to release the connection before allocating another one.
		// */
		// post.abort();
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(result.toString());
			return new JSONObject(result);
		} else {
			return null;
		}
	}

	/**
	 * 重载请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static JsonDTO postRequest(String url) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(Constant.BASE_URL + url);
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				10000);
		// 发送POST请求
		HttpResponse httpResponse = httpClient.execute(post);
		// post.abort();
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			return getJsonDTO(result);
		} else {
			return null;
		}
	}

	/**
	 * 将服务器响应字符串转换成JSON数据
	 * 
	 * @param retSrc
	 * @return
	 */
	private static JsonDTO getJsonDTO(String retSrc) {
		// 生成 JSON 对象
		JsonDTO jsonDto = new JsonDTO();
		JSONObject result;
		try {

			result = new JSONObject(retSrc);
			jsonDto.setData(result.get("data"));
			jsonDto.setLog(result.getString("error"));
			jsonDto.setStatus(result.getInt("code"));
		} catch (JSONException e) {
			return null;
		}

		return jsonDto;
	}

}
