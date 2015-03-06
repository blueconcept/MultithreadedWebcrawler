import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Retrievers pages for parser.
 * @author thang
 *
 */
public class Retriever extends Thread {
	private ParserRetrieverBridge bridge;
	private int max_pages;
	private String startingPage;
	boolean test = false;
	
	/**
	 * Constructor
	 * @param page
	 * @param inputbridge
	 * @param max
	 */
	public Retriever(String page, ParserRetrieverBridge inputbridge, int max) {
		startingPage = page;
		max_pages = max;
		bridge = inputbridge;
		
	}
	
	/**
	 * Code ran when started
	 */
	public void run() {
		int current = 0;
		
		bridge.time = System.currentTimeMillis();
		bridge.addPage(getPage(startingPage));
		
		while(current < max_pages) {
			//get url
			Document currentPage;
			
			try {
				currentPage = Jsoup.connect(bridge.getUrl()).get();
				bridge.addPage(currentPage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			current += 1;
		}
		
		bridge.retrieverDone = true;
		
	    if(test==true) {
		      System.out.println("Retriever Terminating");
		}
		
	}
	
	/**
	 * Gets the page from the web with a url.
	 * @param url
	 * @return
	 */
	private Document getPage(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
