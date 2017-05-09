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
 * @Description: ������ַ��ȡ����
 * @author: Bardon
 * @date: 2017��5��1�� ����1:34:52
 * @version1.0
 */
public class ReportsUtil {

	/**
	 * @throws IOException 
	 * ��ȡ��ҳԴ����
	 * @Title: getHtmlUri
	 * @Author: Bardon
	 * @Time: 2017��5��1�� ����1:36:13
	 * @params: @param url ��ַ
	 * @params: @param encoding ����
	 * @params: @return
	 * @return: String
	 * @throws
	 */
	public static String getHtmlUri(String url,String encoding){
		//��Ϊȫ�ֱ��������ܹر�
		StringBuffer buffer=new StringBuffer();
		URL urlobj=null;
		URLConnection uc=null;
		InputStreamReader isr=null;
		//����
		BufferedReader reader=null;
		try {
			//�õ���ַ
			urlobj= new URL(url);
			//������
			uc=urlobj.openConnection();
			//��������ǽ
			uc.setRequestProperty("User-Agent", "java");
			//IO�� �����ļ�������
			isr=new InputStreamReader(uc.getInputStream(),encoding);
			//��Ч��ȡ
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
	 * ����Դ���� ��ȡ����
	 * @Title: findMessage
	 * @Author: Bardon
	 * @Time: 2017��5��1�� ����1:55:05
	 * @params: @param url
	 * @params: @return
	 * @return: List<HashMap<String,Object>>
	 * @throws
	 */
	public static List<HashMap<String, Object>> findMessage(String url){
		List<HashMap<String, Object>> datas=new ArrayList<HashMap<String,Object>>();
		//�õ���ҳԴ����
		String htmlsource=getHtmlUri(url, "utf-8");
		/*
		 * ����ץȡ
		 * �ҵ�ĳ��ҳԴ�룬�鿴Դ�룬�ҵ���Ч��Ϣ
		 * ��ȡ��ҳ����������ȡ������Ϣ
		 * */
		//����Դ��
		Document document=Jsoup.parse(htmlsource);
		//��ȡcategory-area ��������
		Element element=document.getElementsByClass("category-area").get(0);
		//��ȡ����a
		Elements elements=element.getElementsByTag("a");
		
		//�������е�a��ǩ
		for (Element element2 : elements) {
			//����һ��HashMap���ϴ洢����
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
	 * ��ȡhttp://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E6%AD%A6%E6%B1%89&kw=java%E5%BC%80%E5%8F%91%E5%B7%A5%E7%A8%8B%E5%B8%88&sm=0&p=1
	 * ��ҳ����
	 * @Title: getJobInfo
	 * @Author: Bardon
	 * @Time: 2017��5��6�� ����9:13:27
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
			//����һ��HashMap���ϴ洢����
			map=new HashMap<String, String>();
			//��˾��
			String textTitle=element2.getElementsByClass("gsmc").text();
			//ְλ��
			String jobName=element2.getElementsByClass("zwmc").text();
			//нˮ
			String money=element2.getElementsByClass("zwyx").text();
			//����ʱ��
			String fadate=element2.getElementsByClass("gxsj").text();
			//�����ص�
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
	 * �����Ƶ�����
	 * @Title: getResourceByUrl
	 * @Author: Bardon
	 * @Time: 2017��5��6�� ����9:18:12
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
			//����һ��HashMap���ϴ洢����
			map=new HashMap<String, String>();
			//ͼƬ
			String imgSrc=element2.getElementsByTag("img").attr("src");
			//�Ƶ�title
			String title=element2.getElementsByTag("img").attr("alt");
			//�Ƶ��ַ
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
