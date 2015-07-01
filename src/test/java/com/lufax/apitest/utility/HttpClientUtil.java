package com.lufax.apitest.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class HttpClientUtil {

	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	final static String charsetName = "UTF-8";
	
	public static String get(String url) throws ClientProtocolException, IOException {
		return get(url, null);
	}

	public static String get(String url, String cookiePolicy) throws ClientProtocolException, IOException {
		
		logger.info("HttpClientUtil 发送请求,method: GET,TIME : " + System.currentTimeMillis() + ",URL : " + url);
		
		HttpClient httpClient = new DefaultHttpClient();
		if (cookiePolicy != null) {
			HttpClientParams.setCookiePolicy(httpClient.getParams(), cookiePolicy);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet httpGet = new HttpGet(url);
		
		try {
			String response = httpClient.execute(httpGet, responseHandler);

			logger.info("HttpClientUtil 收到数据,method: GET,TIME : " + System.currentTimeMillis() + ",URL : " + url);
			return response;
		} catch (ClientProtocolException e) {
			System.out.println("[ERROR] HttpClientUtil.get() error: url is [" + url + "]");
			logger.error("HttpClientUtil 请求错误,method: GET,URL : " + url);
			throw e;
		} catch (IOException e) {
			System.out.println("[ERROR] HttpClientUtil.get() error: url is [" + url + "]");
			logger.error("HttpClientUtil 请求错误,method: GET,URL : " + url);
			throw e;
		} finally {
			httpGet.abort();
			httpClient.getConnectionManager().shutdown();
		}
	}

	

	public static String post(String url, Map<String, String> parameters, Map<String,String> cookieMap,HttpClient httpClient) throws UnsupportedEncodingException, ClientProtocolException, IOException {
		return post(url, parameters, charsetName, cookieMap,httpClient);
	}

	public static String post(String url, Map<String, String> etersparameters, String encoding, Map<String,String> cookieMap,HttpClient httpClient) throws UnsupportedEncodingException, ClientProtocolException, IOException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<Entry<String, String>> set = etersparameters.entrySet();
		for (Entry<String, String> entry : set) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return post(url, new UrlEncodedFormEntity(nvps, encoding), cookieMap, httpClient);
	}

	
	
	public static String post(String url, HttpEntity parameter,HttpClient httpClient) throws UnsupportedEncodingException, ClientProtocolException, IOException {
		return post(url, parameter, null, httpClient);
	}

	public static String post(String url, HttpEntity parameter,  Map<String,String> cookieMap,HttpClient httpClient) throws UnsupportedEncodingException, ClientProtocolException, IOException {
		
		logger.info("HttpClientUtil 发送请求,method: POST,TIME : " + System.currentTimeMillis() + ",URL : " + url);
		
		

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost httpPost = new HttpPost(url);
		try {
			if(null != cookieMap){
				Iterator it = cookieMap.keySet().iterator();
				String cookie="";
				while(it.hasNext()){
					String c = (String)it.next();
					cookie+=c+"="+cookieMap.get(c)+";";
				}
				httpPost.addHeader(new BasicHeader("Cookie",cookie));
			}

			httpPost.setEntity(parameter);
			String response = httpClient.execute(httpPost, responseHandler);
			logger.info("HttpClientUtil 收到数据,method: POST,TIME : " + System.currentTimeMillis() + ",URL : " + url);
			logger.info("HttpClientUtil 收到数据,method: POST,result: " + response.replace("\r", "").replace("\n", ""));
			return response;
		} catch (UnsupportedEncodingException e) {
			System.out.println("[ERROR] HttpClientUtil.post() error: url is [" + url + "]");
			logger.error("HttpClientUtil 请求错误,method: POST,URL : " + url);
			throw e;
		} catch (ClientProtocolException e) {
			System.out.println("[ERROR] HttpClientUtil.post() error: url is [" + url + "]");
			logger.error("HttpClientUtil 请求错误,method: POST,URL : " + url);
			throw e;
		} catch (IOException e) {
			System.out.println("[ERROR] HttpClientUtil.post() error: url is [" + url + "]");
			logger.error("HttpClientUtil 请求错误,method: POST,URL : " + url);
			throw e;
		} finally {
			httpPost.abort();
			//shttpClient.getConnectionManager().shutdown();
		}
	}
	
	public static String doPost(String url,String value) throws MalformedURLException,IOException{
        HttpURLConnection urlconn = null;
        BufferedReader rd = null;
        StringBuffer sb = new StringBuffer ();
        
        try{
	        urlconn = (HttpURLConnection) new URL (url).openConnection ();
	        urlconn.getRequestProperties ();
	        urlconn.setRequestProperty ("content-type", "application/json; charset="+charsetName);
	        urlconn.setRequestMethod ("POST");
	        urlconn.setConnectTimeout (10000);
	        urlconn.setReadTimeout (10000);
	        urlconn.setDoInput (true);
	        urlconn.setDoOutput (true);
	        urlconn.getOutputStream ().write (value.getBytes(charsetName));
	        urlconn.getOutputStream ().close ();
	
	        rd = new BufferedReader (new InputStreamReader (urlconn.getInputStream (),charsetName));
	
	        String temp = null;
	        temp = rd.readLine ();
	        while (temp != null) {
	            sb.append (temp);
	            temp = rd.readLine ();
	        }
        }catch(MalformedURLException ex){
        	throw ex;
        }catch(IOException ex){
        	throw ex;
        }finally{
        	try{
        		if(rd != null)
        			rd.close();
        	}catch(Exception fx){
        		fx.printStackTrace();
        	}
        	try{
        		if(urlconn != null)
        			urlconn.disconnect();
        	}catch(Exception fx){
        		fx.printStackTrace();
        	}
        }
        return sb.toString ();
    }

}
