import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for starting threads.
 * @author thang
 *
 */
public class Webcrawler {


	/**
	 * Main 
	 * @param args
	 */
	public static void main(String[] args) {
		//build bridges
		
		
		ParserRetrieverBridge prbridge = new ParserRetrieverBridge();
		ParserAnalyzerBridge pabridge = new ParserAnalyzerBridge();
		AnalyzerGUIBridge agbridge = new AnalyzerGUIBridge();
		
		//start gui
		UserInterface gui = new UserInterface(agbridge);
		
		//getting input
		//max number of pages
		System.out.print("Max Number of Pages: ");
		Scanner scanner = new Scanner(System.in);
		int max = scanner.nextInt();
	
		
		System.out.print("Url: ");
		Scanner scanUrl = new Scanner(System.in);
		String url = scanner.next();
	
		System.out.print("Keywords: ");
		Scanner scankeywords = new Scanner(System.in);
		String keywords = scankeywords.next();

		ArrayList<String> keywordsList = makeKeyWords(keywords);
		
		Retriever retriever = new Retriever(url, prbridge, max);
		Parser parser = new Parser(prbridge, max, pabridge);
		Analyzer analyzer = new Analyzer(pabridge, keywordsList, agbridge);
		UserInterface ui = new UserInterface(agbridge);

		retriever.start();
		parser.start();
		analyzer.start();
		ui.start();
	
	}
	
	
	/**
	 * Creates the keyword array from the input 
	 * @param keywords
	 * @return
	 */
	private static ArrayList<String> makeKeyWords(String keywords) {
		ArrayList<String> keywordList = new ArrayList<String>();
		Scanner scanner = new Scanner(keywords);
		while(scanner.hasNext()) {
			keywordList.add(scanner.next());
		}
		return keywordList;
	}

}
