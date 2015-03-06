import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Parses through page to get url and sends text to analyzer while sending url back to Retriever.
 * @author thang
 *
 */
public class Parser extends Thread {
	
	ArrayList<String> urlHistory;
	ArrayList<String> textList;
	ParserRetrieverBridge bridge;
	ParserAnalyzerBridge analyzerbridge;
	String startingUrl;
	final int Max_pages;
	private boolean test=false;
	
	/**
	 * Constructor
	 * @param inputBridge
	 * @param max
	 * @param analyzer
	 */
	public Parser(ParserRetrieverBridge inputBridge, int max, ParserAnalyzerBridge analyzer) {
		bridge = inputBridge;
		Max_pages = max;
		analyzerbridge = analyzer;
		urlHistory = new ArrayList<String>();
		textList = new ArrayList<String>();
	}
	
	/**
	 * Code to run when started
	 */
	public void run() {
		int current = 0;
		
		try {
			sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ArrayList<String> pages = new ArrayList<String>();
		
		while(current < Max_pages) {
			
			
			//get text
			Document currentpage = bridge.getPage();
			
		
			
			String[] currentLinks = getLinks(currentpage);
			
			//pages.add(currentpage.url[0]);
			 
			//parse through text
			//System.out.println("Parsing through: " + currentpage.url);
			
			//textList.add(currentpage.text);
			analyzerbridge.putText(currentpage.text());
			
			//get urlList		
			//put urlist into analyzer bridge
			analyzerbridge.urlList.add(currentLinks);
			
			
			//checks against history
			for(int i=0; i<currentLinks.length; i+=1) {
				if(!urlHistory.contains(currentLinks[i])) {
					bridge.addUrl(currentLinks[i]);
					break;
				} else {
					urlHistory.add(currentLinks[i]);
				}
			}

			current += 1;

			if(bridge.retrieverDone) {
				break;
			}
		}
		
		analyzerbridge.pageretrieved = bridge.pageCount;
		analyzerbridge.startingtime = bridge.time;
		analyzerbridge.parserDone = true;
	    if(test==true) {
		      System.out.println("Parser Terminating");
		}
		//System.out.println("ArrayList: " + pages.toString());
	}
	
	
	/**
	 * Get urls from page.
	 * @param page
	 * @return
	 */
	private String[] getLinks(Document page) {
		Elements links;
		
		links = page.select("a[href]");
		String[] stringList = new String[links.size()];
		
		int i = 0;
		for(Element x : links) {
			stringList[i] = x.attr("abs:href");
			
		}
		
		return stringList;
	}

}
