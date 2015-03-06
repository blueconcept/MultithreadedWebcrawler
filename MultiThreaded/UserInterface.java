import java.util.HashMap;

/**
 * Display output and takes input.
 * @author thang
 *
 */
public class UserInterface extends Thread {
	
	AnalyzerGUIBridge mybridge;
	HashMap<String, Integer> frequencyCount;
	private boolean test=false;
	
	public UserInterface(AnalyzerGUIBridge bridge) {
		mybridge = bridge;
		
		//lanuchgui
		
		
	}
	
	/**
	 * Code ran when started
	 */
	public void run() {
		//get gui information (will sleep until other processes are done)
		frequencyCount = mybridge.get();
		
		
		//display information
		//average url per page
	    if(test==true) {
		      System.out.println("GUI Terminating");
		}
		display(); 
	}
	
	/**
	 * Displays output.
	 */
	private void display() {
//		HashMap<String, Double> averageCount;
//		double averageWordsPerPage;
//		double urlsPerPage;
//		double timePerPage;
//		int totalPagesRetrieved;
//		long totalrunningtime;
		System.out.println("Total Pages Retrieved: " + mybridge.totalPagesRetrieved);
		System.out.println("Average Words Per Page: " + mybridge.averageWordsPerPage);
		System.out.println("Urls Per Page: " + mybridge.urlsPerPage);
		System.out.println("Total Time: " + mybridge.totalrunningtime);
		System.out.println("Time Per Page: " + mybridge.timePerPage);
		//System.out.println("Keyword   Average   Count ");
		for(String i : frequencyCount.keySet()) {
			
			//System.out.println(i+" "+mybridge.averageCount.get(i)+" "+frequencyCount.get(i));
			System.out.println("Keyword: " + i);
			System.out.println("Per Page: " + mybridge.averageCount.get(i));
			System.out.println("Count: "+frequencyCount.get(i));
		}
	
		
	}

}
