import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * The parser class. It parses the webpage for words and links.
 * 
 * @author Group
 * 
 */
public class Parser {

	private Document my_initial_page;
	private List<String> my_keywords_list;
	private Map<String, Integer> my_keyword_map;
	private int my_max;

	private Stack<String> my_document_stack;
	private Retriever my_retriever;
	public ArrayList<String> my_text;
	public Elements my_urls;
	public Analyzer my_analyzer;

	/**
	 * The parser constructor.
	 * 
	 * 
	 * @param the_retriever
	 * @param the_initial_page
	 * @param the_keywords_list
	 * @param the_max
	 */
	public Parser(final Retriever the_retriever, final String the_initial_page,
			final List<String> the_keywords_list, final int the_max) {
		my_retriever = the_retriever;
		my_keywords_list = the_keywords_list;
		my_keyword_map = new HashMap<String, Integer>();
		my_max = the_max;
		setInitialPage(the_initial_page);
		my_document_stack = new Stack<String>();

		my_text = new ArrayList<String>();
		my_urls = new Elements();

		my_document_stack.push(the_initial_page);

		ArrayList<String> history = new ArrayList<String>();

		int i = the_max;

		while (!my_document_stack.isEmpty() && i > 0) {
			if (history.contains(my_document_stack.peek())) {
				my_document_stack.pop();
			} else {
				my_initial_page = my_retriever
						.addPage(my_document_stack.peek());
				history.add(my_document_stack.pop());
				getAllLinks();
				getAllText();
				i--;
			}
		}

		callAnalyzer();
	}

	public void callAnalyzer() {
		my_analyzer = new Analyzer();
		my_analyzer.mapping(my_text, my_urls.size());
	}

	public Analyzer getAnalyzer() {
		return my_analyzer;
	}

	private final void setInitialPage(final String the_initial_page) {
		try {
			my_initial_page = Jsoup.connect(the_initial_page).get();

		} catch (final IOException the_exception) {
			the_exception.printStackTrace();
		}
	}

	public void getAllLinks() {
		Elements links = null;
		String title = my_initial_page.title();

		System.out.println("title : " + title);
		links = my_initial_page.select("a[href]");

		System.out.printf("Number of links: %d \n", links.size());

		for (Element x : links) {
			my_document_stack.push(x.attr("abs:href"));
			my_urls.add(x);
			System.out.println(x.attr("abs:href"));
		}
	}

	public void getAllText() {
		List<String> strings = new ArrayList<String>();
		String text = my_initial_page.text();
		StringTokenizer tokens = new StringTokenizer(text, " ");

		while (tokens.hasMoreTokens()) {
			String temp = tokens.nextToken();
			strings.add(temp);
			my_text.add(temp.toLowerCase());
		}
	}
}
