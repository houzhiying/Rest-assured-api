package com.lufax.apitest.utility;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtil {

	private static final Logger log = LoggerFactory
			.getLogger(HttpRequestUtil.class);

	public static HttpResponse executeGETMethod(String url) {
		String result = null;

		CloseableHttpClient client = HttpClients.custom()
				.setDefaultRequestConfig(defaultReqConf()).build();

		String get_path = (url).trim();
		HttpGet get = new HttpGet(get_path.trim());
        get.setHeader("User-Agent","User-Agent");
		HttpResponse resp = null;
		try {

			resp = client.execute(get);



		} catch (IOException e) {
			
			log.error("执行无参数GET请求失败-[{}]", e.getMessage());
			close(client);
			//return result;
		}
		return resp;
	}

	public static String executePOSTMethod(String url) {
		String result = null;

		CloseableHttpClient client = HttpClients.custom()
				.setDefaultRequestConfig(defaultReqConf()).build();

		String post_path = (url).trim();
		HttpPost post = new HttpPost(post_path);

		HttpResponse resp = null;
		try {
			resp = client.execute(post);
		} catch (IOException e) {
			log.error("执行无参数POST请求失败-[{}]", e.getMessage());
			close(client);
			return result;
		}

		int code = resp.getStatusLine().getStatusCode();
		if (code == 200)
			try {
				result = EntityUtils.toString(resp.getEntity(), "UTF-8");
			} catch (IOException e) {
				log.error("无参数POST请求读取响应消息失败-[{}]", e.getMessage());
				return result;
			}
		else {
			log.error("无参数POST请求返回信息失败,错误代码-[{}]", Integer.valueOf(code));
		}

		close(client);

		return result;
	}

	public static String executeMediaTypePOSTMethod(String url, String media_type,
			String data) {
		String result = null;

		CloseableHttpClient client = HttpClients.custom()
				.setDefaultRequestConfig(defaultReqConf()).build();

		String post_path = (url).trim();
		HttpPost post = new HttpPost(post_path);
		StringEntity se = null;
		try {
			se = new StringEntity(data, "UTF-8");
		} catch (Exception e) {
			log.error("准备Body数据失败-[{}]", e.getMessage());
			close(client);
			return result;
		}
		se.setContentType(media_type);
		post.setEntity(se);

		HttpResponse resp = null;
		try {
			resp = client.execute(post);
		} catch (IOException e) {
			log.error("执行Body参数POST请求失败-[{}]", e.getMessage());
			close(client);
			return result;
		}

		int code = resp.getStatusLine().getStatusCode();
		if (code == 200)
			try {
				result = EntityUtils.toString(resp.getEntity(), "UTF-8");
			} catch (IOException e) {
				log.error("Body参数POST请求读取响应消息失败-[{}]", e.getMessage());
				return result;
			}
		else {
			log.error("Body参数POST请求返回信息失败,错误代码-[{}]", Integer.valueOf(code));
		}

		close(client);

		return result;
	}

	protected static RequestConfig defaultReqConf() {
		RequestConfig reqconf = RequestConfig.custom().setCookieSpec(
				"best-match").setExpectContinueEnabled(true)
				.setStaleConnectionCheckEnabled(true).setConnectTimeout(2000)
				.setConnectionRequestTimeout(4000).build();

		return reqconf;
	}

	protected static void close(CloseableHttpClient client) {
		if (null != client)
			try {
				client.close();
				log.info("关闭HTTP请求成功.");
			} catch (IOException e) {
				log.error("关闭HTTP连接失败-[{}]", e.getMessage());
			}
	}

}

