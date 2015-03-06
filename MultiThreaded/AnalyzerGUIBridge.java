import java.util.HashMap;

/**
 * Go between for Analyzer and GUI.
 * @author thang
 *
 */
public class AnalyzerGUIBridge {
	
	boolean available = false;
	HashMap<String, Integer> frequencyCount;
	HashMap<String, Double> averageCount;
	double averageWordsPerPage;
	double urlsPerPage;
	double timePerPage;
	int totalPagesRetrieved;
	long totalrunningtime;
	private boolean test=false;
	
	/**
	 * Get frequency map.
	 * @return
	 */
	  public synchronized HashMap<String, Integer> get() {
		      while (available == false) {
		         try {
		            wait();
		         }
		         catch (InterruptedException e) {
		         }
		      }
		      if(test==true) {
			      System.out.println("get()");
			  }
		      available = false;
		      notify();
		      return frequencyCount;
		   }
	   
	  /**
	   * Set frequency map
	   * @param frequency
	   */
	   public synchronized void put(HashMap<String, Integer> frequency) {
		    while (available == true) {
		         try {
		            wait();
		         }
		         catch (InterruptedException e) { 
		         } 
		      }
		    	if(test==true) {
			      System.out.println("put()");
			    }
		    
		      frequencyCount = frequency;
		      available = true;
		      notify();
		   }
}
