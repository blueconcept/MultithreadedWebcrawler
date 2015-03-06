import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Analyzes text from Parser and sents analysis to UI.
 * @author thang
 *
 */
public class Analyzer extends Thread {

	ArrayList<String> my_keywords;
	ParserAnalyzerBridge my_bridge;
	ArrayList<String> text_list;
	int pagecount;
	int totalwords = 0;
	private AnalyzerGUIBridge ag_bridge;
	HashMap<String, Double> averageCount;
	HashMap<String, Integer> frequencyCount;
	private boolean test=false;
	
	/**
	 * Constructor
	 * @param pabridge
	 * @param keywords
	 * @param bridge
	 */
	public Analyzer(ParserAnalyzerBridge pabridge, ArrayList<String> keywords, AnalyzerGUIBridge bridge) {
		my_keywords = keywords;
		my_bridge = pabridge;
		ag_bridge = bridge;
		text_list = new ArrayList<String>();
		frequencyCount = new HashMap<String, Integer>();
	}
	
	/**
	 * Code when started.
	 */
	public void run() {
		while(true) {
			text_list.add(my_bridge.getText());
			
			//find way to break out of this
			if(my_bridge.parserDone) {
				break;
			}
		}

		analyzeText();
		//frequency count
		ag_bridge.put(frequencyCount);
		
		
		//get total pages retrieved
		pagecount = my_bridge.pageretrieved;
		ag_bridge.totalPagesRetrieved = pagecount;
		
		//words per page
		ag_bridge.averageWordsPerPage = totalwords/pagecount;
		
		//urls per page
		int totalUrls = totalCount(my_bridge.urlList);
		ag_bridge.urlsPerPage = totalUrls/pagecount;
		
		//average count per page
		averageCount = getAveragerCount(frequencyCount, pagecount);
		ag_bridge.averageCount = averageCount;
		
		//total time
		long endtime = System.currentTimeMillis();
		ag_bridge.totalrunningtime =  endtime - my_bridge.startingtime;
		
		//average time per page
		ag_bridge.timePerPage = ag_bridge.totalrunningtime/pagecount;
		
	    if(test==true) {
		      System.out.println("Analyzer Terminating");
		}
	}

	/**
	 * Gets total count of urls.
	 * @param urlList
	 * @return
	 */
	private int totalCount(ArrayList<String[]> urlList) {
		int total = 0;
		for(int i=0; i<urlList.size(); i+=1) {
			for(int j=0; j<urlList.get(i).length;j+=1) {
				total +=1;
			}
		}
		return total;
	}

	/**
	 * Set frequency count and total words.
	 */
	private void analyzeText() {
		for(int i=0; i<text_list.size();i+=1) {
			Scanner scanner = new Scanner(text_list.get(i));
			while(scanner.hasNext()) {
				totalwords +=1;
				
				String word = scanner.next();
				if(my_keywords.contains(word)) {
					frequency(word);
				}
			}
		}
		
		
	}

	/**
	 * Set frequency count of keywords
	 * @param next
	 */
	private void frequency(String next) {
		// TODO Auto-generated method stub
		
		if(frequencyCount.containsKey(next)) {
			frequencyCount.put(next, frequencyCount.get(next));
		} else{
			frequencyCount.put(next, 1);
		}
	}

	/**
	 * Returns a average count hash map with the pagecount and frequency count.
	 * @param frequencyCount2
	 * @param pagecount2
	 * @return
	 */
	private HashMap<String, Double> getAveragerCount(
			HashMap<String, Integer> frequencyCount2, int pagecount2) {
		HashMap<String, Double> averageOutput = new HashMap<String, Double>();
		for(String i : frequencyCount2.keySet()) {
			double average = frequencyCount2.get(i)/pagecount2;
			averageOutput.put(i, average);
		}
		return averageOutput;
	}
	
}
