import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.nodes.Document;

/**
 * Go between Parser and Retriever
 * @author thang
 *
 */
public class ParserRetrieverBridge {

	private boolean Retrieveravailable = false;
	private boolean Parseravailable = false;
	volatile Queue<Document> pageQueue;
	volatile Queue<String> urlQueue;
	ArrayList<String> history;
	boolean retrieverDone = false;
	long time;
	int pageCount = 0;
	boolean test = false;
	
	
	public ParserRetrieverBridge() {
	
		pageQueue = new LinkedList<Document>();
		urlQueue = new LinkedList<String>();
		time = 0;
		history = new ArrayList<String>();
	}
	
	//Retriever stores document
	public synchronized void addPage(Document page) {
		
	      while (Retrieveravailable == true) {
	          try {
	             wait();
	          }
	          catch (InterruptedException e) { 
	          } 
	      }
	      
	      if(test==true) {
		      System.out.println("addPage");
		  }
	      
	      //System.out.println("addingPage " + page.url[0]);  
	      pageQueue.add(page);
	      pageCount += 1;
	      
	      Retrieveravailable = true;
	      notify();
	}
	
	//Retriever gives Parser - Document
	public synchronized Document getPage() {
	
		while (Retrieveravailable == false) {
	         try {
	            wait();
	         }
	         catch (InterruptedException e) {
	         }
	      }
		 
	    if(test==true) {
		      System.out.println("getPage");
		}
		
		Retrieveravailable = false;
	    notify();
		
		return pageQueue.poll();
	    
	}
	
	//Parser gives Retriever  url
	public synchronized String getUrl() {
		
		while (Parseravailable == false) {
	         try {
	            wait();
	         }
	         catch (InterruptedException e) {
	         }
	      }

	    if(test==true) {
		     System.out.println("getUrl");
		}
		
		Parseravailable = false;
	      notify();
		//Return action
		return urlQueue.poll();
	    //  return bridgeUrl;
	}
	
	//Parser stores urlList
	//Put
	public synchronized void addUrl(String url) {
		 while (Parseravailable == true) {
	          try {
	             wait();
	          }
	          catch (InterruptedException e) { 
	          } 
	      }
		 
	      urlQueue.add(url);
	      if(test==true) {
	      System.out.println("addUrl");
	      }
	      
	      //bridgeUrl = url;
	      Parseravailable = true;
	      notify();
	}
	
}
