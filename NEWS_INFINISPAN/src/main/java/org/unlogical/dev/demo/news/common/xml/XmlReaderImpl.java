package org.unlogical.dev.demo.news.common.xml;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.unlogical.dev.demo.news.common.abs.AbstractBaseService;
import org.unlogical.dev.demo.news.common.util.DateUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service("xmlReader")
public class XmlReaderImpl extends AbstractBaseService<XmlReaderImpl> implements XmlReader{
	
	@Async
	public void rssRead(String rssUrl, String category) {
		try{
			Document doc;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
		    URL u = new URL(rssUrl);
		    URLConnection uc = u.openConnection();
		    InputStream is = uc.getInputStream();
		    BufferedInputStream bi = new BufferedInputStream(is);
			doc = builder.parse(bi);
			is.close();
			bi.close();
			NodeList nodes = doc.getElementsByTagName("item");
			NodeList ns = null;
			Node nd = null;
			DBObject n = null, 
					 o = new BasicDBObject("category",category);
			List<String> cList = null;
			for(int i=0; i<nodes.getLength(); i++){
				ns = nodes.item(i).getChildNodes();
				n = new BasicDBObject();
				cList = new ArrayList<String>();
				for(int j=0; j<ns.getLength(); j++){
					nd = ns.item(j);
					if(nd.getNodeName().trim().equals("category")){
						cList.add(nd.getTextContent().trim());
					}else if(nd.getNodeName().trim().equals("dc:creator")){
						n.put("creator", nd.getTextContent());
					}else if(nd.getNodeName().trim().equals("dc:date") || nd.getNodeName().trim().equals("pubDate")){
						n.put("date", nd.getTextContent());
					}else n.put(nd.getNodeName().trim(), nd.getTextContent());
				}
				n.put("category", category);
				n.put("tags", cList.toArray());
	            n.put("regDate", DateUtil.getToday("yyyyMMddHHmmss")+i);
	            if(log.isDebugEnabled()){
	            	System.out.println("!!$$"+n);
	            }
	            o.put("title", n.get("title"));
	          //  if(getDBCollection("News").findOne(o) == null)
	            	getDBCollection("News").insert(n);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
