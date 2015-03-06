import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Go between for Parser and Analyzer
 * @author thang
 *
 */
public class ParserAnalyzerBridge {
	
	boolean available = false;
	String text;
	boolean parserDone = false;
	int pageretrieved;
	ArrayList<String[]> urlList;
	long startingtime;
	boolean test = false;
	
	/**
	 * Constructor
	 */
	public ParserAnalyzerBridge() {
		
		pageretrieved = 0;
		urlList = new ArrayList<String[]>();
	}
	
	//get
	/**
	 * Get text
	 * @return
	 */
	public synchronized String getText() {
		while (available == false) {
	         try {
	            wait();
	         }
	         catch (InterruptedException e) {
	         }
	      }
	    if(test==true) {
		      System.out.println("getText");
		}
		available = false;
	    notify();
		return text;
	}

	/**
	 * Set text
	 * @param textPage
	 */
	public synchronized void putText(String textPage) {
	      while (available == true) {
	          try {
	             wait();
	          }
	          catch (InterruptedException e) { 
	          } 
	      }
	      if(test==true) {
		      System.out.println("putText");
		  } 
	      
		text = textPage;
		available = true;
		notify();
	}


}
