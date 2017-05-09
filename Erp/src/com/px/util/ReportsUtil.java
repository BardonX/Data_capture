package com.px.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @ClassName: ReportsUtil
 * @Description: 根据网址获取数据
 * @author: Bardon
 * @date: 2017年5月1日 下午1:34:52
 * @version1.0
 */
public class ReportsUtil {

	/**
	 * @throws IOException 
	 * 获取网页源代码
	 * @Title: getHtmlUri
	 * @Author: Bardon
	 * @Time: 2017年5月1日 下午1:36:13
	 * @params: @param url 网址
	 * @params: @param encoding 编码
	 * @params: @return
	 * @return: String
	 * @throws
	 */
	public static String getHtmlUri(String url,String encoding){
		//作为全局变量，才能关闭
		StringBuffer buffer=new StringBuffer();
		URL urlobj=null;
		URLConnection uc=null;
		InputStreamReader isr=null;
		//缓冲
		BufferedReader reader=null;
		try {
			//拿到网址
			urlobj= new URL(url);
			//打开链接
			uc=urlobj.openConnection();
			//跳过防火墙
			uc.setRequestProperty("User-Agent", "java");
			//IO流 建立文件输入流
			isr=new InputStreamReader(uc.getInputStream(),encoding);
			//高效读取
			reader=new BufferedReader(isr);
			String temp=null;
			while((temp=reader.readLine())!=null){
				buffer.append(temp);
				//System.out.println(temp);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * @throws IOException 
	 * 处理源代码 获取数据
	 * @Title: findMessage
	 * @Author: Bardon
	 * @Time: 2017年5月1日 下午1:55:05
	 * @params: @param url
	 * @params: @return
	 * @return: List<HashMap<String,Object>>
	 * @throws
	 */
	public static List<HashMap<String, Object>> findMessage(String url){
		List<HashMap<String, Object>> datas=new ArrayList<HashMap<String,Object>>();
		//拿到网页源代码
		String htmlsource=getHtmlUri(url, "utf-8");
		/*
		 * 数据抓取
		 * 找到某网页源码，查看源码，找到有效信息
		 * 获取网页、遍历、提取有用信息
		 * */
		//解析源码
		Document document=Jsoup.parse(htmlsource);
		//获取category-area 区域数据
		Element element=document.getElementsByClass("category-area").get(0);
		//获取所有a
		Elements elements=element.getElementsByTag("a");
		
		//遍历所有的a标签
		for (Element element2 : elements) {
			//创建一个HashMap集合存储数据
			HashMap<String, Object> map=new HashMap<String, Object>();
			String[] text=element2.text().split("\\(");
			String ct=text[0];
			String num=text[1].replaceAll("\\)", "");
			map.put("text", ct);
			map.put("num", num);
			datas.add(map);
		}
		
		return datas;
	}
	
	/**
	 * 获取http://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E6%AD%A6%E6%B1%89&kw=java%E5%BC%80%E5%8F%91%E5%B7%A5%E7%A8%8B%E5%B8%88&sm=0&p=1
	 * 网页数据
	 * @Title: getJobInfo
	 * @Author: Bardon
	 * @Time: 2017年5月6日 下午9:13:27
	 * @params: @param url
	 * @params: @param encoding
	 * @params: @return
	 * @return: List<HashMap<String,String>>
	 * @throws
	 */
	public static List<HashMap<String, String>> getJobInfo(String url,String encoding){
		String htmlsource=getHtmlUri(url, encoding);
		Document document=Jsoup.parse(htmlsource);
		//div id
		Element element=document.getElementById("newlist_list_content_table");
		//table
		Elements elements=element.getElementsByClass("newlist");
		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map=null;
		for (Element element2 : elements) {
			//创建一个HashMap集合存储数据
			map=new HashMap<String, String>();
			//公司名
			String textTitle=element2.getElementsByClass("gsmc").text();
			//职位名
			String jobName=element2.getElementsByClass("zwmc").text();
			//薪水
			String money=element2.getElementsByClass("zwyx").text();
			//发布时间
			String fadate=element2.getElementsByClass("gxsj").text();
			//工作地点
			String address=element2.getElementsByClass("gzdd").text();
			map.put("textTitle", textTitle);
			map.put("jobName", jobName);
			map.put("money", money);
			map.put("fadate", fadate);
			map.put("address", address);
			list.add(map);
		}
		return list;
	}
	/**
	 * 分析酒店数据
	 * @Title: getResourceByUrl
	 * @Author: Bardon
	 * @Time: 2017年5月6日 下午9:18:12
	 * @params: @param url
	 * @params: @param encoding
	 * @params: @return
	 * @return: List<HashMap<String,String>>
	 * @throws
	 */
	public static List<HashMap<String, String>> getResourceByUrl(String url,String encoding){
		String htmlsource=getHtmlUri(url, encoding);
		Document document=Jsoup.parse(htmlsource);
		//div id
		Element element=document.getElementById("hotel_list");
		//table
		Elements elements=element.getElementsByClass("searchresult_list");
		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map=null;
		for (Element element2 : elements) {
			//创建一个HashMap集合存储数据
			map=new HashMap<String, String>();
			//图片
			String imgSrc=element2.getElementsByTag("img").attr("src");
			//酒店title
			String title=element2.getElementsByTag("img").attr("alt");
			//酒店地址
			String htdaddress=element2.getElementsByClass("searchresult_htladdress").text();
			map.put("imgSrc", imgSrc);
			map.put("title", title);
			map.put("htdaddress", htdaddress);
			list.add(map);
		}
		return list;
	}
	
	public static void main(String[] args)  {
		//getHtmlUri("http://www.qq.com", "gbk");
		//getHtmlUri("http://cs.ganji.com/site/s/_java/", "utf-8");
		//ReportsUtil util=new ReportsUtil();
		//List<HashMap<String, Object>> findMessage = util.findMessage("");
		//System.out.println(findMessage);
		/*List<HashMap<String, Object>> list=findMessage("http://cs.ganji.com/site/s/_java/");
		System.out.println(list);*/
		//List<HashMap<String, String>> list=getJobInfo("http://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E6%AD%A6%E6%B1%89&kw=java%E5%BC%80%E5%8F%91%E5%B7%A5%E7%A8%8B%E5%B8%88&sm=0&p=1", "utf-8");
		//System.out.println(list);
		
		List<HashMap<String, String>> list=getResourceByUrl("http://hotels.ctrip.com/hotel/guangzhou32#ctm_ref=hod_hp_sb_list", "utf-8");
		System.out.println(list.size());
	}
}
