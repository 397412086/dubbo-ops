package com.array;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author dengping
 * @Date 2017/11/28 14:32
 **/
public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String doGet(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return resultString;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "GBK");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return resultString;
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (isIpNull(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isIpNull(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isIpNull(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (isIpNull(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (isIpNull(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private static boolean isIpNull(String ip) {
		return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
	}

	/**
	 * 下载文件
	 *
	 * @param url
	 *            http://www.xxx.com/img/333.jpg
	 * @param dest
	 *            xxx.jpg/xxx.png/xxx.txt
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String downloadFileByPost(String url, String dest, List<NameValuePair> param)
			throws ClientProtocolException, IOException {
		// 生成一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(param));
		HttpResponse response = httpclient.execute(httpPost);
		String fileName = getFileName(response);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		File file = new File(dest + File.pathSeparator + fileName);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
			}
			fout.flush();
			fout.close();
		} catch (Exception e ) {
			e.printStackTrace();
			return e.getMessage();
		}finally {
			// 关闭低层流。
			in.close();
		}
		httpclient.close();
		return null;
	}

	/**
	 * 获取response header中Content-Disposition中的filename值
	 * @param response
	 * @return
	 */
     public static String getFileName(HttpResponse response) {
		 Header contentHeader = response.getFirstHeader("Content-Disposition");
		 String filename = null;
		 if (contentHeader != null) {
			 HeaderElement[] values = contentHeader.getElements();
			 if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					 try {
						 //filename = new String(param.getValue().toString().getBytes(), "utf-8");
						//filename=URLDecoder.decode(param.getValue(),"utf-8");
						 filename = param.getValue();
					 } catch (Exception e) {
						 e.printStackTrace();
					 }
				}
			 }
		 }
		 return filename;
	 }

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String s = HttpUtils.doGet("http://www.baidu.com");
		System.out.println(s);

		String s2 = HttpUtils.doGet("http://116.228.64.55:28082/FundDataApi/Chart/78880000/000709/json/wfsy.json");
		System.out.println(s2);
	}
}
